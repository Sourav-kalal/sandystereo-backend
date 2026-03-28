# SandyStereo Backend (Spring Boot)

Low-level design (LLD) oriented Spring Boot backend with PostgreSQL + Flyway + JWT security.

## LLD Overview
- **Presentation layer**: REST controllers under `controller` package.
- **Service layer**: Business/auth logic in `service` and `service.impl`.
- **Persistence layer**: JPA entities + repositories mapped 1:1 to DB tables.
- **Security**: Stateless JWT auth (`/api/auth/register`, `/api/auth/login`) and role-based authorization.
- **Database migration**: Flyway migration `V1__full_reset_rebuild.sql` executes the full reset/rebuild script and seeds sample data.

## Run
```bash
mvn spring-boot:run
```

## Important ENV
- `DATABASE_URL`
- `DATABASE_USERNAME`
- `DATABASE_PASSWORD`
- `JWT_SECRET` (Base64-encoded key)

## API Surface
- Auth: `/api/auth/register`, `/api/auth/login`
- CRUD resources:
  - `/api/users`
  - `/api/profiles`
  - `/api/userroles`
  - `/api/courses`
  - `/api/events`
  - `/api/enrollments`
  - `/api/admissions`
  - `/api/payments`
  - `/api/classes`
  - `/api/instructorcourses`
  - `/api/sitesettings`
