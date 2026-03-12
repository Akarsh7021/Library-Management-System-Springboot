# Server Setup Guide

This guide assumes a copy of the source code is already downloaded and available.

## Software Stack

Our software stack is as follows:

- **Java 23 With Spring** — core backend, including hosting web content.
- **PostgreSQL** — database software of choice. we used version 18.
- **Web Browser** — any web browser should be able to run and connect to the frontend.

## Installing Required Software for Building

The minimum software required to build and run the software is as follows:

- Oracle JDK 23: https://www.oracle.com/java/technologies/javase/jdk23-archive-downloads.html
- PostgreSQL 18 (other versions may work but are
  untested): https://www.enterprisedb.com/downloads/postgres-postgresql-downloads
- Optionally, any Java IDE

A script to automatically install the required software on Windows systems equipped with PowerShell and Winget is as
follows:

```
winget install Oracle.JDK.23
winget install PostgreSQL.PostgreSQL.18
```

If installing PostgreSQL manually, set a username and password for the superuser account, as it will be necessary later.
If installing automatically, the username and password will both be `postgres` by default. Similarly, if the
configuration asks which port to host the service, leave as the default (5432) or make note of the change.

You may need to restart your computer to start all services and add all executables to the `$PATH`.

On Windows, the Path variable is not always updated by the PostgreSQL installer. To fix this, run the PowerShell command
when you open the terminal to run any commands in this guide:

```bash
$ $env:Path += ";C:\Program Files\PostgreSQL\18\bin\"
```

## Setting Up the Database

First, ensure the database is working and running on your system by running the `psql`(`.exe`) utility from the
terminal. If the utility does not authenticate automatically, append the `-U `(username) argument to this and all other
PostgreSQL commands. If it connects with no error, exit with `\q`.

Next, load the example data file into the database. Follow these steps:

1. Open your terminal of choice (on Windows, usually CMD, PowerShell, or Windows Terminal)
2. Navigate to the downloaded source code, ensuring you are in the folder containing the `extra` folder.
3. Run the `pg_restore` utility to restore the backup data from the backup.
    1. On Windows: `pg_restore.exe -C -d info2413 .\extra\info2413-sample.sql`
    2. On Unix-like Systems: `pg_restore -C -d postgres ./extra/info2413-sample.sql`
4. Observe the console output for any errors. If there are none, continue.
5. Update the PostgreSQL section in `src/main/resources/application.properties` with the correct host, port, and
   credentials. An example configuration is shown below.

Find this:

```properties
## PostgreSQL
spring.datasource.url=jdbc:postgresql://info2413-postgresql.lonk-ladon.ts.net:5432/info2413
spring.datasource.username=info2413
spring.datasource.password=info2413
```

Change to:

```properties
## PostgreSQL
spring.datasource.url=jdbc:postgresql://<HOSTNAME_OR_IP_ADDRESS>:<PORT_OR_5432>/info2413
spring.datasource.username=<USERNAME>
spring.datasource.password=<PASSWORD>
```

Example:

```properties
## PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/info2413
spring.datasource.username=postgres
spring.datasource.password=postgres
```

## Building and Running the Software

To build the software and connect to the interface, the database must be set up and running. Follow these steps:

1. Open your terminal of choice (on Windows, usually CMD, PowerShell, or Windows Terminal)
2. Navigate to the downloaded source code, ensuring you are in the folder containing `gradlew`(`.bat`)
3. Run the Gradle build
    1. On Windows: `.\gradlew.bat bootRun`
   2. On Unix-like Systems: `./gradlew bootRun`
4. Connect your web browser to http://localhost:8080/

To stop the server, focus the terminal window and press `^c` (Ctrl+C).
