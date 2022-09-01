#To-Do
## Use Case to-do
 - Admin features
   - [x] Admin Login
   - [x] Add User
   - [ ] Update Target User
   - [ ] Delete Target User
 - Employee Features
   - [x] Employee Login
   - [ ] View My reimbursements
   - [x] Submit new reimbursement
   - [ ] View Reimbursement Details
   - [ ] Update Reimbursement (Only in pending state)
 - Finance manager features
   - [x] Manager Login
   - [x] View ALL reimbursements
   - [x] Filter Reimbursements 
   - [ ] View Reimbursement Details
   - [ ] Approve/Deny Reimbursement
   
## Id-Naming Convention
- ers_user_roles
  - 001 = ADMIN
  - 002 = MANAGER
  - 003 = EMPLOYEE
- ers_reimbursement_statuses
  - 001 = PENDING
  - 002 = DENIED
  - 003 = APPROVED_UNPAID
  - 004 = APPROVED_PAID
- ers_reimbursement_types
  - 001 = LODGING
  - 002 = TRAVEL
  - 003 = FOOD
  - 004 = OTHER

## Checkpoints
#### 25Aug2022
- Project requirements delivered

#### 29Aug2022
- Remote repository is created and is being kept up to date
- Core model classes are created
- Registration/Authentication/User operations in progress

#### 1Sept2022
- Local DB instance running
- App to DB connection made
- Specified tables created with proper constraints
- Registration/Authentication/User operations complete
- Reimbursement operations in progress
- Basic persistence layer operations in progress
- Testing of business logic is in progress

#### 05Sept2022
- Registration/Authentication web endpoints are accessible and functional
- Reimbursement web endpoints are accessible and functional
- User passwords are encrypted when persisted to the DB
- Testing of business logic is in progress

#### 07Sept2022
- Project Presentations
