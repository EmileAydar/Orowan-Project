# ArcelorMittal Project

This software engineering project is a partnership between **IMT Mines Alès** and **ArcelorMittal**,
a well-known company in the world of metallurgy.

It is a team project, we are using the SCRUM method, which is an Agile method. 
This method imposes rigor on the project's progress, with one meeting per week.

The collaborators are : [Emile Aydar](https://github.com/xXMagIkZzR4mBOXx),
[Amine H.](https://github.com/ggleKg) and [myself](https://github.com/YooZiiX).

The project is based on the following specifications:

- Read CSV
- Store CSV data in database
- Compute Orowan model every 200ms with last available value
- Compute average of last 5 orowan friction coefficient every 1s and store them into database
- Human-Machine Interface:
  - For every one:
    - Login / Logout
  - For the Worker:
    - Stand ID
    - Compute time of orowan
    - Friction coefficient factor
  - For the Process Engineer:
    - Add / Remove / Update User
    - Add / Remove / Update User rights
    - Change application settings:
      - Enable / Disable stand
      - Change level 2 inputs range
  

- Improve Orowan


- Human-Machine Interface:
  - Add curves for the operator:
    - Friction
    - Roll speed
    - Sigma
  - Create a password protection for the users


- GRPC:
  - Use the LII simulator based gRpc
  - Use the orowan model based on gRpc
  

- Time Series Database:
  - Store Level II data into a Time Series Database
  - Store Orowan results into a Time Series Database
  - Show data from the Time Series Database into the Human-Machine Interface

*More information is available in PDF format in the "project" folder.*

As we saw in the specifications, the project requires a Human-Machine Interface,
so we are going to use the **MVC** architecture.

What's the **MVC** architecture?

MVC mean's : **M**odel **V**iew **C**ontroller,
a **M**odel contains the data to be displayed on Interface,
a **V**iew contains the presentation of the graphical Interface,
and a **C**ontroller contains the logic for user actions.

## CSV Files

Firstly, we set up a database using WampServer to store the data from the various CSV files.
Be carefull with float-type numbers, because the CSV float separator is '**,**' and the SQL separator is '**.**',
so you need to replace '**,**' per '**.**'.

For every SQL queries, we will use **PreparedStatement** to avoid usebug.

## Orowan

What's **Orowan** ? 

**Orowan** is a model that takes certain values as input to calculate other values,
such as the Friction coefficient.
In this project, **Orowan** is an executable, which must be used with Java.

As required by the specifications, we have to use **Orowan** every 200ms,
Then store values in the WampServer SQL database.

Every 5 Orowan's iterations, we compute the average Friction coefficient value.
And we store the values in the database.

## User

Through our specifications, we can see that we need to implement CRUD functionnality, for User and User rights.
(**C**reate - **R**etrieve - **U**pdate - **D**elete)

### User's Password
To store the user's password in the database, we used a hash method (MD5).
We also created a random password generation feature.

### User's Roles
The specification shows 2 user roles (Worker & Process Engineer), but he took the liberty of making a new one,
the Administrator.

## Human-Machine Interfaces (HMI)

The specification shows that differents user doesn't have the same HMI

### For every one

The first screen on opening the software is the Login screen.
There is no registration for users. It is the role of administrator to create an account for all new users.

After logging on, the user has access to the stand ID, to the logout feature using the logout button and finally the working interface.
This is where his role is important, as he may or may not have access to parts of the software.

### For Worker

This part is accessible by **any role**. In this part, the user can start or stop computing value using Orowan.
Furthermore, the user can see the evolution of the following curves :
- Friction
- Roll speed
- Sigma

### For Process Engineer

This part is accessible by **Process Engineer** and **Administrator**. In this part, the user can enable or disable a stand.

### For Administrator

This part is accessible only by **Administrator**. In this part,
Administrator can **C**reate, **R**etrieve, **U**pdate and **D**elete users.
But also add and remove user rights.