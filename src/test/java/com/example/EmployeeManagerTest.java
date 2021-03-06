package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmployeeManagerTest {


    @Test
    @DisplayName("Test function for findAll() in Interface EmployeeRepository")
    void listingEmployeesTest() {
        EmployeeRepository employeeRepository = new TestEmployeeRepository();
        EmployeeFilter employeeFilter = new EmployeeFilter(employeeRepository);

        var actual = employeeFilter.filter();

        assertThat(actual).containsExactly(new Employee("Manager", 2500.0).getId());

    }

    @Test
    @DisplayName("Test function for findAll() in Interface EmployeeRepository using Mockito")
    void listingEmployeesTestWithMockito() {
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        when(employeeRepository.findAll()).thenReturn(List.of(new Employee("Manager", 2500.0),
                new Employee("Exec", 3500.0)));

        EmployeeFilter employeeFilter = new EmployeeFilter(employeeRepository);

        var actual = employeeFilter.filter();

        assertThat(actual).containsExactly(new Employee("Manager", 2500.0).getId());
    }

    @Test
    @DisplayName("Test function for save() in Interface EmployeeRepository")
    void saveEmployeeTest() {
        EmployeeRepository employeeRepository = new TestEmployeeRepository();
        EmployeeFilter employeeFilter = new EmployeeFilter(employeeRepository);
        Employee employee = new Employee("Exec-001", 8000.0);
        Employee actual = employeeFilter.saveEmployee(employee);

        assertTrue(actual.getId().equals(new Employee("Exec-001", 8000.0).getId()));
    }

    @Test
    @DisplayName("Test function for Id setters and getters")
    void employeeIdTest(){
        Employee employee = new Employee("Mgr001", 4500.0);
        employee.setId("Mgr002");

        assertThat(employee.getId()).isEqualTo("Mgr002");
        assertFalse(employee.getId().equals("Mgr001"));

    }

    @Test
    @DisplayName("Test function for Salary setters and getters")
    void employeeSalaryTest(){
        Employee employee = new Employee("Mgr001", 4500.0);
        employee.setSalary(5500.0);

        assertThat(employee.getSalary()).isEqualTo(5500.0);
        assertFalse(employee.getSalary() == 4500.0);

    }

    @Test
    @DisplayName("Test function for boolean paid")
    void employeePaidTest(){
        Employee employee = new Employee("Mgr001", 4500.0);
        employee.setPaid(true);

        assertTrue(employee.isPaid());
    }

    @Test
    @DisplayName("Test function for toString()")
    void toStringTest(){
        Employee employee = new Employee("Mgr001", 4500.0);
        String expected = "Employee [id=Mgr001, salary=4500.0]";

        assertEquals(expected, employee.toString());
    }

    @Test
    @DisplayName("Changing status of employee being paid after payEmployee()")
    void payEmployeesListTest() {

        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        when(employeeRepository.findAll()).thenReturn(List.of(new Employee("Exec000", 2500.0),
                new Employee("Exec001", 3500.0),
                new Employee("Exec004", 6500.0),
                new Employee("Exec002", 4500.0),
                new Employee("Exec003", 5500.0),
                new Employee("Exec006", 7500.0)
        ));

        EmployeeFilter employeeFilter = new EmployeeFilter(employeeRepository);
        BankService bankService = new TestBankService();
        EmployeeManager employeeManager = new EmployeeManager(employeeRepository, bankService);

        int expectedResult = employeeManager.payEmployees();
        assertEquals(expectedResult, 5);

    }

}