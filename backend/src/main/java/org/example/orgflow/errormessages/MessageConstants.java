package org.example.orgflow.errormessages;


import lombok.experimental.UtilityClass;


@UtilityClass
public  class MessageConstants {

    //Department Messages
    public static final String DEPARTMENT_NOT_FOUND = "department.not.found";
    public static final String DEPARTMENT_ALREADY_EXISTS = "department.already.exists";
    public static final String NO_DEPARTMENTS_FOUND = "departments.not.found";
    public static final String DEPARTMENT_MANAGER_NOT_ASSIGNED = "department.manager.not.assigned";
    public static final String NO_EMPLOYEES_IN_DEPARTMENT = "department.no.employees";


    //Manager Messages
    public static final String MANAGER_NOT_FOUND = "manager.not.found";
    public static final String MANAGER_ALREADY_EXISTS = "manager.already.exists";
    public static final String NO_MANAGERS_FOUND = "managers.not.found";
    public static final String MANAGER_DEPENDENCY_DEPARTMENT = "manager.dependency.department";
    public static final String MANAGER_DEPENDENCY_EMPLOYEE = "manager.dependency.employee";
    public static final String MANAGER_DEPENDENCY_BOTH ="manager.dependency.both";


    //Employee Messages
    public static final String EMPLOYEE_ALREADY_EXISTS= "employee.already.exists";
    public static final String MANAGER_DIFFERENT_DEPARTMENT = "manager.different.department";
    public static final String EMPLOYEE_NOT_FOUND= "employee.not.found";
    public static final String NO_EMPLOYEES_FOUND= "employees.not.found";
    public static final String HIRE_DATE_REQUIRED = "hire.date.required";
    public static final String NO_EMPLOYEES_FOUND_AFTER_DATE = "no.employees.found.after.date";

    //Salary Percentage Method Messages
    public static final String SALARY_PERCENTAGE_REQUIRED = "salary.percentage.required";
    public static final String INVALID_SALARY_PERCENTAGE = "salary.percentage.invalid";


    //paging Messages
    public static final String INVALID_PAGE_NUMBER = "page.number.invalid";
    public static final String INVALID_PAGE_SIZE = "page.size.invalid";

}
