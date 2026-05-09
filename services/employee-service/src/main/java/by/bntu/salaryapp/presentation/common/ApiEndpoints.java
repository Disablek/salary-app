package by.bntu.salaryapp.presentation.common;

public final class ApiEndpoints {
    private ApiEndpoints() {}

    public static final class Employee {
        private Employee() {}
        public static final String BASE = "/api/employees";
        public static final String BY_ID = BASE + "/{employeeId}";
        public static final String SEARCH = BASE + "/search";
    }

    public static final class Position {
        private Position() {}
        public static final String BASE = "/api/positions";
        public static final String BY_ID = BASE + "/{positionId}";
        public static final String SEARCH = BASE + "/search";
    }

    public static final class Experience {
        private Experience() {}
        public static final String BASE = "/api/experiences";
        public static final String BY_ID = BASE + "/{experienceId}";
        public static final String SEARCH = BASE + "/search";
    }

    public static final class Qualification {
        private Qualification() {}
        public static final String BASE = "/api/qualifications";
        public static final String BY_ID = BASE + "/{qualificationId}";
        public static final String SEARCH = BASE + "/search";
    }

    public static final class Subject {
        private Subject() {}
        public static final String BASE = "/api/subjects";
        public static final String BY_ID = BASE + "/{subjectId}";
        public static final String SEARCH = BASE + "/search";
    }

    public static final class User {
        private User() {}

        public static final String BASE = "/api/users";
        public static final String CURRENT_USER = BASE + "/me";
        public static final String BY_ID = BASE + "/{userId}";
        public static final String CHANGE_PASSWORD = BASE + "/{userId}/password";
        public static final String SEARCH = BASE + "/search";
    }

    public static final class Auth {
        private Auth() {}
        public static final String REGISTER = "/api/auth/register";
    }
}
