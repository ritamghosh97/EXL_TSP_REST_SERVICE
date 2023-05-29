package com.exlservice.timesheet.security.constant;

public class TimesheetSecurityJDBCQueryConstants {
    public static final String FIND_USERS_BY_USERNAME_QUERY = "select id, password, active from employee where id=?";

    public static final String FIND_AUTHORITIES_BY_USERNAME_QUERY = "select emp_id, role from roles where emp_id=?";
}
