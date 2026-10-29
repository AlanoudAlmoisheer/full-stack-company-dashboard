-- Managers
INSERT INTO manager (name, email, salary, hire_date) VALUES
                                                         ('Ali Ahmed',   'Ali@gmail.com',   9500.00, '2019-03-15'),
                                                         ('Sara Omar','sara@gmail.com', 9000.00, '2020-07-01'),
                                                         ('sami Ali','sami@gmail.com', 8800.00, '2018-11-20');

-- Departments
INSERT INTO department (department_name, location, manager_id) VALUES
                                                                   ('Engineering', 'Riyadh', (SELECT id FROM manager WHERE email = 'Ali@gmail.com')),
                                                                   ('Marketing',   'Jeddah', (SELECT id FROM manager WHERE email = 'sara@gmail.com')),
                                                                   ('Finance',     'Riyadh', (SELECT id FROM manager WHERE email = 'sami@gmail.com'));

-- Employees
INSERT INTO employee (name, job_title, email, salary, hire_date, department_id, manager_id) VALUES
                                                                                                ('Yousef Ali', 'Software Developer', 'yousef@gmail.com', 5200.00, '2022-01-10',
                                                                                                 (SELECT id FROM department WHERE department_name = 'Engineering'),
                                                                                                 (SELECT id FROM manager WHERE email = 'Ali@gmail.com')),
                                                                                                ('Noura Saleh', 'QA Engineer', 'noura@gmail.com', 5000.00, '2022-05-23',
                                                                                                 (SELECT id FROM department WHERE department_name = 'Engineering'),
                                                                                                 (SELECT id FROM manager WHERE email = 'Ali@gmail.com')),
                                                                                                ('Hassan Ali', 'Marketing Specialist', 'hassan@gmail.com', 4600.00, '2021-09-06',
                                                                                                 (SELECT id FROM department WHERE department_name = 'Marketing'),
                                                                                                 (SELECT id FROM manager WHERE email = 'sara@gmail.com')),
                                                                                                ('Reem Saleh', 'Content Writer', 'reem@gmail.com', 4800.00, '2023-02-13',
                                                                                                 (SELECT id FROM department WHERE department_name = 'Marketing'),
                                                                                                 (SELECT id FROM manager WHERE email = 'sara@gmail.com')),
                                                                                                ('Faisal Ahmed', 'Financial Analyst', 'faisal@gmail.com', 5100.00, '2020-10-19',
                                                                                                 (SELECT id FROM department WHERE department_name = 'Finance'),
                                                                                                 (SELECT id FROM manager WHERE email = 'sami@gmail.com'));