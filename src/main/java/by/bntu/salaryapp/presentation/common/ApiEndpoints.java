package by.bntu.salaryapp.presentation.common;

public final class ApiEndpoints {
    private ApiEndpoints() {}

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
