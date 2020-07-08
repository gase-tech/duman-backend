package com.kstech.duman.constants;

import java.util.Arrays;
import java.util.List;

public interface ApplicationConstants {
    String SPRING_PROFILE_DEVELOPMENT = "dev";
    String SPRING_PROFILE_TEST = "test";
    String SPRING_PROFILE_PRODUCTION = "prod";

    String SPRING_PROFILE_DEFAULT = "spring.profiles.default";

    String ZONE_ID = "Europe/Istanbul";

    List<String> EXCEPT_LOG_METHODS = Arrays.asList("info");
}
