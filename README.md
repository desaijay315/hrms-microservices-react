# hrms-microservices-react

SaaS product based on an HRMS (Human Resource Management System) 

### 1. Tech Stack

**Backend:**
- **Language:** Java (Spring Boot 3+) microservices
- **Architecture:** Microservices for scalability and maintainability.
- **Database:** Consider using SQL (e.g., MySQL) for structured data like entire users records.
- **ORM:** Hibernate for database interactions.
- **APIs:** REST for client-server communication.
- **Testing:** JUnit for unit testing, Mockito for mocking.

**Frontend:**
- **Framework:** React for building a dynamic and responsive UI.
- **State Management:** Redux or Context API for managing application state.
- **Styling:** CSS frameworks like Tailwind CSS or Bootstrap for UI design.
- **Testing:** Jest for unit tests and Cypress for end-to-end testing.

**Infrastructure:**
- **Containerization:** Docker for containerizing applications.
- **Orchestration:** Kubernetes for managing containerized applications.
- **CI/CD:** Jenkins or GitHub Actions for continuous integration and delivery.

### 2. Business Requirements

- **Employee Management:** Adding, updating, and removing employee records.
- **Attendance Tracking:** Monitoring check-ins, check-outs, and leaves.
- **Payroll Management:** Automating salary calculations, deductions, and tax computations.
- **Performance Evaluation:** Tracking employee performance and conducting reviews.
- **Recruitment Process:** Managing job postings, applications, interviews, and hiring.
- **Learning and Development:** Tracking employee training, certifications, and progress.

### 3. Stories and Feature Breakdown

1. **Employee Onboarding:**
   - Story: As an HR, I want to add new employees to the system so that their records are digitally stored.
   - Tasks: Create forms for input, validate data, save to database, generate employee ID.

2. **Attendance System:**
   - Story: As an employee, I want to check in and check out daily so that my attendance is recorded.
   - Tasks: Implement check-in/out feature, track IP or use biometrics for validation.

3. **Payroll System:**
   - Story: As an HR, I want to process salaries based on attendance and performance.
   - Tasks: Calculate base pay, deductions, and bonuses, generate payslips, handle bank transfers.

4. **Performance Reviews:**
   - Story: As a manager, I want to review employee performance so that feedback and ratings can be provided.
   - Tasks: Create review forms, set review cycles, notifications for pending reviews.

5. **Recruitment Module:**
   - Story: As a recruiter, I want to manage job postings and applications so that the hiring process is streamlined.
   - Tasks: Post new jobs, track applicants, schedule interviews, store interview feedback.

### 4. Granular Details

- **Authentication & Authorization:**
  - Implement JWT for secure, token-based authentication.
  - Role-based access control (RBAC) for different user types (HR, employee, manager).
- **Frontend Components:**
  - Reusable React components for forms, tables, modals, and dashboards.
- **Backend Services:**
  - Separate microservices for each core module (attendance, payroll, recruitment).
- **Security Measures:**
  - Use HTTPS, sanitize inputs to prevent SQL injection, employ CORS policies.
- **Data Backup and Recovery:**
  - Regular database backups and a plan for disaster recovery.

### 5. Authentication + Authorization

- **JWT (JSON Web Tokens):** Secure method for representing claims between two parties. Use it for authentication and transferring information.
- **Spring Security:** Utilize Spring Security for configuring web security. Implement JWT filter to intercept requests for token validation.
- **OAuth2:** For external logins (e.g., Google, LinkedIn), integrate OAuth2 for authorization.
- **Roles and Permissions:** Define roles (Admin, HR, Employee) and permissions (e.g., read, write, update) in the database. Use Spring Security annotations to protect endpoints based on roles.


### SQL

### Employee Management

```sql
CREATE TABLE departments (
    department_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255)
);

CREATE TABLE employees (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    department_id INT,
    manager_id INT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(50),
    hire_date DATE NOT NULL,
    role VARCHAR(100),
    status VARCHAR(50),
    FOREIGN KEY (department_id) REFERENCES departments(department_id),
    FOREIGN KEY (manager_id) REFERENCES employees(employee_id)
);
```

### Authentication and Authorization

```sql
CREATE TABLE roles (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT UNIQUE,
    username VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    last_login TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

CREATE TABLE user_roles (
    user_id INT,
    role_id INT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);
```

### Attendance Tracking

```sql
CREATE TABLE attendance_records (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT,
    check_in_time TIMESTAMP,
    check_out_time TIMESTAMP,
    date DATE NOT NULL,
    status VARCHAR(50),
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

CREATE TABLE leave_requests (
    request_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    type VARCHAR(50),
    status VARCHAR(50),
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);
```

### Revised Payroll and Salary Structure

#### Salaries

```sql
CREATE TABLE salaries (
    salary_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT,
    base_salary DECIMAL(10, 2) NOT NULL,
    effective_from DATE NOT NULL,
    effective_to DATE,
    currency VARCHAR(3),
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);
```

#### Payroll Records (Adjusted)

```sql
CREATE TABLE payroll_records (
    payroll_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT,
    salary_id INT,
    period_start DATE NOT NULL,
    period_end DATE NOT NULL,
    gross_salary DECIMAL(10, 2) NOT NULL,
    net_salary DECIMAL(10, 2) NOT NULL,
    deductions_total DECIMAL(10, 2) NOT NULL,
    bonuses_total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id),
    FOREIGN KEY (salary_id) REFERENCES salaries(salary_id)
);
```

#### Bonuses

```sql
CREATE TABLE bonuses (
    bonus_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT,
    amount DECIMAL(10, 2) NOT NULL,
    description VARCHAR(255),
    date DATE NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);
```

#### Deductions

```sql
CREATE TABLE deductions (
    deduction_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT,
    amount DECIMAL(10, 2) NOT NULL,
    description VARCHAR(255),
    date DATE NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);



```

#### Tenants

- Add Tenant ID Column and Foreign Key Constraints

```sql

CREATE TABLE tenants (
    tenant_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--- Employees and Departments
ALTER TABLE employees ADD COLUMN tenant_id INT NOT NULL;
ALTER TABLE departments ADD COLUMN tenant_id INT NOT NULL;

--- Attendance Records
ALTER TABLE employees ADD CONSTRAINT fk_employees_tenant_id FOREIGN KEY (tenant_id) REFERENCES tenants(tenant_id);
ALTER TABLE departments ADD CONSTRAINT fk_departments_tenant_id FOREIGN KEY (tenant_id) REFERENCES tenants(tenant_id);

--- Leave Requests
ALTER TABLE leave_requests ADD COLUMN tenant_id INT NOT NULL;
ALTER TABLE leave_requests ADD CONSTRAINT fk_leave_requests_tenant_id FOREIGN KEY (tenant_id) REFERENCES tenants(tenant_id);

--- Payroll Records
ALTER TABLE payroll_records ADD COLUMN tenant_id INT NOT NULL;
ALTER TABLE payroll_records ADD CONSTRAINT fk_payroll_records_tenant_id FOREIGN KEY (tenant_id) REFERENCES tenants(tenant_id);

--- Salaries
ALTER TABLE salaries ADD COLUMN tenant_id INT NOT NULL;
ALTER TABLE salaries ADD CONSTRAINT fk_salaries_tenant_id FOREIGN KEY (tenant_id) REFERENCES tenants(tenant_id);

--- Bonuses
ALTER TABLE bonuses ADD COLUMN tenant_id INT NOT NULL;
ALTER TABLE bonuses ADD CONSTRAINT fk_bonuses_tenant_id FOREIGN KEY (tenant_id) REFERENCES tenants(tenant_id);

--- Deductions
ALTER TABLE deductions ADD COLUMN tenant_id INT NOT NULL;
ALTER TABLE deductions ADD CONSTRAINT fk_deductions_tenant_id FOREIGN KEY (tenant_id) REFERENCES tenants(tenant_id);

--- Job Postings
ALTER TABLE job_postings ADD COLUMN tenant_id INT NOT NULL;
ALTER TABLE job_postings ADD CONSTRAINT fk_job_postings_tenant_id FOREIGN KEY (tenant_id) REFERENCES tenants(tenant_id);

--- Applications
ALTER TABLE applications ADD COLUMN tenant_id INT NOT NULL;
ALTER TABLE applications ADD CONSTRAINT fk_applications_tenant_id FOREIGN KEY (tenant_id) REFERENCES tenants(tenant_id);

--- Courses and Enrollments (for Learning and Development)
ALTER TABLE courses ADD COLUMN tenant_id INT NOT NULL;
ALTER TABLE courses ADD CONSTRAINT fk_courses_tenant_id FOREIGN KEY (tenant_id) REFERENCES tenants(tenant_id);

ALTER TABLE enrollments ADD COLUMN tenant_id INT NOT NULL;
ALTER TABLE enrollments ADD CONSTRAINT fk_enrollments_tenant_id FOREIGN KEY (tenant_id) REFERENCES tenants(tenant_id);


```
