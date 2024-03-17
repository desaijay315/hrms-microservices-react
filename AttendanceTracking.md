# Attendance Tracking Service API Endpoints

## Employee Attendance

- **POST /attendance/clock-in**  
  Record an employee's clock-in time.  
  Payload could include `employeeId` and `clockInTime`.

- **POST /attendance/clock-out**  
  Record an employee's clock-out time.  
  Payload could include `employeeId` and `clockOutTime`.

- **GET /attendance/records/{employeeId}**  
  Retrieve attendance records for a specific employee.  
  Query parameters could include date filters like `startDate` and `endDate`.

- **GET /attendance/record/{recordId}**  
  Retrieve a specific attendance record by its ID.

## Leave Management

- **POST /attendance/leaves/request**  
  Submit a leave request.  
  Payload could include `employeeId`, `leaveType`, `startDate`, `endDate`, and `reason`.

- **PUT /attendance/leaves/{leaveId}/approve**  
  Approve a leave request.  
  Payload could include the `approverId`.

- **PUT /attendance/leaves/{leaveId}/reject**  
  Reject a leave request.  
  Payload could include the `approverId` and `reasonForRejection`.

- **GET /attendance/leaves/requests/{employeeId}**  
  Get all leave requests for a specific employee.

- **GET /attendance/leaves/{leaveId}**  
  Get details of a specific leave request.

## Administration and Reports

- **GET /attendance/reports/summary**  
  Get a summary report of attendance for a specified period.  
  Query parameters could include `startDate`, `endDate`, and optional filters like `departmentId`.

- **GET /attendance/reports/employee-late**  
  Get a report of all employees who clocked in late during a specified period.

- **GET /attendance/reports/absence**  
  Get a report of all absences during a specified period.

## Features and Functionalities

- **Clock-In and Clock-Out Tracking:** The service should allow employees to clock in when they start their work and clock out when they finish. The system would record the timestamp of these events.

- **Leave Request Management:** Employees should be able to request leaves (such as vacation, sick leave, etc.), which managers can then approve or reject.

- **Attendance Record Keeping:** The service should maintain detailed records of employee attendance, including late arrivals and early departures.

- **Absence Tracking:** The system should be able to track unexplained or unscheduled absences.

- **Reporting:** Generate reports for attendance, including summaries for individuals or departments, details of late arrivals, and absence reports for management review.

- **Notifications:** Send notifications to employees and managers for leave requests, approvals, rejections, and reminders for clock-ins.

- **Audit Trails:** Maintain logs for all operations related to attendance for compliance and auditing purposes.

- **Integration Capabilities:** Provide integration options for other services like Payroll, Employee Management, and more, to ensure seamless workflow across the HRMS.
