(function () {
  const safeJsonParse = (value) => {
    if (typeof value !== "string" || value.length === 0) {
      return null;
    }

    try {
      return JSON.parse(value);
    } catch (_error) {
      return null;
    }
  };

  const pathOf = (url) => {
    try {
      return new URL(url, window.location.href).pathname;
    } catch (_error) {
      return "";
    }
  };

  const mapRoleToUi = (role) => {
    if (role === "SUPERUSER" || role === "ROLE_SUPERUSER") {
      return "SUPERUSER";
    }

    if (
      role === "ADMIN" ||
      role === "ROLE_ADMIN" ||
      role === "PAYROLL_SPECIALIST" ||
      role === "ROLE_PAYROLL_SPECIALIST" ||
      role === "SPECIALIST" ||
      role === "ROLE_SPECIALIST"
    ) {
      return "ADMIN";
    }

    return "USER";
  };

  const mapRoleToApi = (role) => {
    if (role === "SUPERUSER" || role === "ROLE_SUPERUSER") {
      return "SUPERUSER";
    }

    if (role === "ADMIN" || role === "ROLE_ADMIN") {
      return "ADMIN";
    }

    return "USER";
  };

  const usernameFromEmail = (email) => {
    if (!email || typeof email !== "string") {
      return "";
    }

    return email.split("@")[0] || email;
  };

  const splitFullName = (fullName) => {
    const parts = String(fullName || "").trim().split(/\s+/).filter(Boolean);
    return {
      lastName: parts[0] || "",
      firstName: parts[1] || parts[0] || "",
      patronymic: parts.slice(2).join(" "),
    };
  };

  const normalizeUser = (user) => {
    if (!user || typeof user !== "object") {
      return user;
    }

    return {
      ...user,
      role: mapRoleToUi(user.role || user.roleName),
      active: user.active ?? user.enabled ?? true,
      username: user.username || usernameFromEmail(user.email),
      surname: user.surname || "",
      createdAt: user.createdAt || new Date().toISOString(),
      updatedAt: user.updatedAt || user.createdAt || new Date().toISOString(),
    };
  };

  const normalizeCatalogItem = (item) => {
    if (!item || typeof item !== "object") {
      return item;
    }

    return {
      ...item,
      name: item.name || item.title || "",
      title: item.title || item.name || "",
      description: item.description || "",
      active: item.active ?? true,
    };
  };

  const normalizeEmployee = (employee) => {
    if (!employee || typeof employee !== "object") {
      return employee;
    }

    const nameParts = splitFullName(employee.fullName);
    const positionId = employee.positionId || employee.position_id || employee.position?.id || "";
    const qualificationId = employee.qualificationId || employee.qualification_id || employee.qualification?.id || "";
    const subjectIds = employee.subjectIds || employee.subjects_id || employee.subjects?.map((subject) => subject.id) || [];
    return {
      ...employee,
      id: employee.id || employee.employeeId || employee.fullName,
      firstName: employee.firstName || nameParts.firstName,
      lastName: employee.lastName || nameParts.lastName,
      patronymic: employee.patronymic || employee.surName || nameParts.patronymic,
      email: employee.email || "",
      phoneNumber: employee.phoneNumber || "",
      hireDate: employee.hireDate || "",
      yearsOfExperience: employee.yearsOfExperience ?? employee.experience?.yearsOfExperience ?? 0,
      positionId,
      position: employee.position || (positionId ? { id: positionId, name: employee.positionName || employee.positionTitle || "" } : null),
      qualificationId,
      qualificationIds: employee.qualificationIds || (qualificationId ? [qualificationId] : []),
      qualifications: employee.qualifications || (qualificationId ? [{ id: qualificationId, name: employee.qualificationName || employee.qualificationTitle || "" }] : []),
      subjectIds,
      subjects: employee.subjects || subjectIds.map((id) => ({ id })),
      experience: employee.experience || { yearsOfExperience: employee.yearsOfExperience ?? 0 },
      active: employee.active ?? true,
    };
  };

  const normalizeDataTable = (table) => {
    if (!table || typeof table !== "object") {
      return table;
    }

    const normalizeCell = (cell) => ({
      ...cell,
      id: cell?.id || "",
      columnId: cell?.columnId || cell?.column_id || "",
      value: cell?.value == null ? "" : String(cell.value),
    });

    const normalizeRow = (row, index) => ({
      ...row,
      id: row?.id || "",
      employeeId: row?.employeeId || row?.employee_id || "",
      order: row?.order ?? index + 1,
      cells: Array.isArray(row?.cells) ? row.cells.map(normalizeCell) : [],
    });

    const normalizeColumn = (column, index) => ({
      ...column,
      id: column?.id || "",
      name: column?.name || column?.title || "",
      title: column?.title || column?.name || "",
      type: String(column?.type || column?.dataType || "STRING").toUpperCase(),
      order: column?.order ?? index + 1,
      formula: column?.formula || "",
    });

    return {
      ...table,
      columns: Array.isArray(table.columns) ? table.columns.map(normalizeColumn) : [],
      rows: Array.isArray(table.rows) ? table.rows.map(normalizeRow) : [],
    };
  };

  const stringifyErrorValue = (value) => {
    if (value == null || typeof value === "string") {
      return value;
    }

    if (Array.isArray(value)) {
      return value.map(stringifyErrorValue).filter(Boolean).join("; ");
    }

    if (typeof value === "object") {
      try {
        return JSON.stringify(value);
      } catch (_error) {
        return String(value);
      }
    }

    return String(value);
  };

  const normalizeErrorPayload = (data) => {
    if (!data || typeof data !== "object" || Array.isArray(data)) {
      return data;
    }

    return {
      ...data,
      message: stringifyErrorValue(data.message),
      error: stringifyErrorValue(data.error),
    };
  };

  const normalizeResponse = (path, data) => {
    const errorSafeData = normalizeErrorPayload(data);
    if (errorSafeData !== data) {
      data = errorSafeData;
    }

    const normalizeList = (mapper) => Array.isArray(data) ? data.map(mapper) : mapper(data);

    if (path.startsWith("/api/users")) {
      return normalizeList(normalizeUser);
    }

    if (path.startsWith("/api/positions") || path.startsWith("/api/qualifications") || path.startsWith("/api/subjects") || path.startsWith("/api/experiences")) {
      return normalizeList(normalizeCatalogItem);
    }

    if (path.startsWith("/api/employees")) {
      return normalizeList(normalizeEmployee);
    }

    if (path.startsWith("/api/datatables")) {
      return normalizeList(normalizeDataTable);
    }

    return data;
  };

  const normalizeUserRequest = (data, method) => {
    const email = data.email || "";
    const next = {
      ...data,
      username: data.username || usernameFromEmail(email),
      surname: data.surname || data.patronymic || "",
      role: mapRoleToApi(data.role),
    };

    if (method === "PUT" && !next.password) {
      delete next.password;
    }

    return next;
  };

  const normalizeCatalogRequest = (data) => ({
    ...data,
    title: data.title || data.name || "",
  });

  const normalizeEmployeeRequest = (data) => {
    const fullName = data.fullName || [data.lastName, data.firstName, data.patronymic].filter(Boolean).join(" ");
    const qualificationId = data.qualification_id || data.qualificationId || (Array.isArray(data.qualificationIds) ? data.qualificationIds[0] : null) || null;
    return {
      ...data,
      fullName,
      position_id: data.position_id || data.positionId || null,
      qualification_id: qualificationId,
      subjects_id: data.subjects_id || data.subjectIds || [],
    };
  };

  const normalizeDataTableRequest = (data) => ({
    ...data,
    ...(Object.prototype.hasOwnProperty.call(data, "name") ? { name: data.name } : {}),
    ...(Object.prototype.hasOwnProperty.call(data, "description") ? { description: data.description || "" } : {}),
    ...(Array.isArray(data.columns) ? {
      columns: data.columns.map((column, index) => ({
        ...(column.id ? { id: column.id } : {}),
        name: column.name || column.title || "",
        type: String(column.type || column.dataType || "STRING").toUpperCase(),
        order: column.order ?? index + 1,
        ...(column.formula ? { formula: column.formula } : {}),
      })),
    } : {}),
    ...(Array.isArray(data.rows) ? {
      rows: data.rows.map((row, rowIndex) => ({
        ...(row.id ? { id: row.id } : {}),
        ...(row.employeeId ? { employeeId: row.employeeId } : {}),
        order: row.order ?? rowIndex + 1,
        cells: Array.isArray(row.cells) ? row.cells.map((cell) => ({
          ...(cell.id ? { id: cell.id } : {}),
          columnId: cell.columnId || cell.column_id,
          value: cell.value == null ? "" : String(cell.value),
        })) : [],
      })),
    } : {}),
  });

  const normalizeRequestBody = (method, path, body) => {
    const data = safeJsonParse(body);
    if (!data || typeof data !== "object" || Array.isArray(data)) {
      return body;
    }

    let normalized = data;
    if (path.startsWith("/api/users")) {
      normalized = normalizeUserRequest(data, method);
    } else if (path.startsWith("/api/positions") || path.startsWith("/api/qualifications") || path.startsWith("/api/subjects") || path.startsWith("/api/experiences")) {
      normalized = normalizeCatalogRequest(data);
    } else if (path.startsWith("/api/employees")) {
      normalized = normalizeEmployeeRequest(data);
    } else if (path.startsWith("/api/datatables")) {
      normalized = normalizeDataTableRequest(data);
    }

    return JSON.stringify(normalized);
  };

  const nativeOpen = XMLHttpRequest.prototype.open;
  const nativeSend = XMLHttpRequest.prototype.send;
  const responseTextDescriptor = Object.getOwnPropertyDescriptor(XMLHttpRequest.prototype, "responseText");
  const responseDescriptor = Object.getOwnPropertyDescriptor(XMLHttpRequest.prototype, "response");

  XMLHttpRequest.prototype.open = function (method, url, ...rest) {
    this.__salaryApiAdapter = {
      method: String(method || "GET").toUpperCase(),
      originalUrl: String(url || ""),
      path: pathOf(url),
    };

    return nativeOpen.call(this, method, url, ...rest);
  };

  XMLHttpRequest.prototype.send = function (body) {
    const meta = this.__salaryApiAdapter;
    if (meta && typeof body === "string") {
      return nativeSend.call(this, normalizeRequestBody(meta.method, meta.path, body));
    }

    return nativeSend.call(this, body);
  };

  const transformedResponse = (xhr, rawValue) => {
    const meta = xhr.__salaryApiAdapter;
    const parsed = safeJsonParse(rawValue);
    if (!meta || parsed == null || xhr.readyState !== 4) {
      return rawValue;
    }

    return JSON.stringify(normalizeResponse(meta.path, parsed));
  };

  if (responseTextDescriptor && responseTextDescriptor.get) {
    Object.defineProperty(XMLHttpRequest.prototype, "responseText", {
      configurable: true,
      enumerable: responseTextDescriptor.enumerable,
      get() {
        return transformedResponse(this, responseTextDescriptor.get.call(this));
      },
    });
  }

  if (responseDescriptor && responseDescriptor.get) {
    Object.defineProperty(XMLHttpRequest.prototype, "response", {
      configurable: true,
      enumerable: responseDescriptor.enumerable,
      get() {
        const value = responseDescriptor.get.call(this);
        if (this.responseType && this.responseType !== "text" && this.responseType !== "json") {
          return value;
        }

        if (this.responseType === "json" && value && typeof value === "object") {
          const meta = this.__salaryApiAdapter;
          return meta ? normalizeResponse(meta.path, value) : value;
        }

        return typeof value === "string" ? transformedResponse(this, value) : value;
      },
    });
  }
})();
