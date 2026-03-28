# Sandy Stereo Backend – cURL API Collection

Use `BASE_URL=http://localhost:8080` (or your server). After **login**, set `TOKEN` to the JWT from the response for all protected endpoints.

---

## 1. Auth (no token)

### Register
```bash
curl -X POST "http://localhost:8080/api/auth/register" \
  -H "Content-Type: application/json" \
  -d '{"email":"student@example.com","password":"yourSecurePassword","roles":["student"]}'
```
`roles`: `["admin"]` | `["instructor"]` | `["student"]` or any combination.

### Login
```bash
curl -X POST "http://localhost:8080/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"email":"student@example.com","password":"yourSecurePassword"}'
```
Use the `token` from the response in: `Authorization: Bearer <token>`.

---

## 2. Users (admin only)

### List users
```bash
curl -X GET "http://localhost:8080/api/users" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Get user by ID
```bash
curl -X GET "http://localhost:8080/api/users/USER_UUID" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Delete user
```bash
curl -X DELETE "http://localhost:8080/api/users/USER_UUID" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

---

## 3. Profiles (admin: full; instructor: list/get)

### List profiles
```bash
curl -X GET "http://localhost:8080/api/profiles" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Get profile by ID
```bash
curl -X GET "http://localhost:8080/api/profiles/PROFILE_UUID" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Create profile (admin)
```bash
curl -X POST "http://localhost:8080/api/profiles" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"user":{"id":"USER_UUID"},"fullName":"John Doe","avatarUrl":"https://example.com/avatar.png"}'
```

### Update profile (admin)
```bash
curl -X PUT "http://localhost:8080/api/profiles/PROFILE_UUID" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"id":"PROFILE_UUID","user":{"id":"USER_UUID"},"fullName":"John Doe Updated","avatarUrl":"https://example.com/avatar2.png"}'
```

### Delete profile (admin)
```bash
curl -X DELETE "http://localhost:8080/api/profiles/PROFILE_UUID" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

---

## 4. Courses (public: list/get; admin: create/update/delete)

### List courses
```bash
curl -X GET "http://localhost:8080/api/courses"
```

### Get course by ID
```bash
curl -X GET "http://localhost:8080/api/courses/COURSE_UUID"
```

### Create course (admin)
```bash
curl -X POST "http://localhost:8080/api/courses" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"title":"Guitar","description":"Learn acoustic and electric guitar.","imageUrl":"https://example.com/guitar.jpg","level":"Intermediate","duration":"12 weeks","isActive":true,"googleFormLink":"https://forms.google.com/...","whatsappNumber":"+91...","upiPrice":2999.00}'
```
`level`: `Beginner` | `Intermediate` | `Advanced`

### Update course (admin)
```bash
curl -X PUT "http://localhost:8080/api/courses/COURSE_UUID" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"id":"COURSE_UUID","title":"Guitar Updated","description":"Updated description","level":"Intermediate","isActive":true}'
```

### Delete course (admin)
```bash
curl -X DELETE "http://localhost:8080/api/courses/COURSE_UUID" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

---

## 5. Events (public: list/get; admin: create/update/delete)

### List events
```bash
curl -X GET "http://localhost:8080/api/events"
```

### Get event by ID
```bash
curl -X GET "http://localhost:8080/api/events/EVENT_UUID"
```

### Create event (admin)
```bash
curl -X POST "http://localhost:8080/api/events" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name":"Annual Concert","description":"Year-end performance","eventDate":"2025-12-31T18:00:00+05:30","bannerUrl":"https://example.com/banner.jpg","googleFormLink":"https://forms.google.com/...","isActive":true}'
```

### Update event (admin)
```bash
curl -X PUT "http://localhost:8080/api/events/EVENT_UUID" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"id":"EVENT_UUID","name":"Annual Concert Updated","eventDate":"2025-12-31T18:00:00+05:30","isActive":true}'
```

### Delete event (admin)
```bash
curl -X DELETE "http://localhost:8080/api/events/EVENT_UUID" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

---

## 6. Classes (admin/instructor: list/get; admin: create/update/delete)

### List classes
```bash
curl -X GET "http://localhost:8080/api/classes" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Get class by ID
```bash
curl -X GET "http://localhost:8080/api/classes/CLASS_UUID" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Create class (admin)
```bash
curl -X POST "http://localhost:8080/api/classes" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"course":{"id":"COURSE_UUID"},"instructor":{"id":"INSTRUCTOR_USER_UUID"},"title":"Week 1 - Basics","scheduledAt":"2025-03-01T10:00:00+05:30","meetLink":"https://meet.google.com/..."}'
```

### Update class (admin)
```bash
curl -X PUT "http://localhost:8080/api/classes/CLASS_UUID" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"id":"CLASS_UUID","course":{"id":"COURSE_UUID"},"title":"Week 1 - Basics Updated","scheduledAt":"2025-03-01T10:00:00+05:30","meetLink":"https://meet.google.com/..."}'
```

### Delete class (admin)
```bash
curl -X DELETE "http://localhost:8080/api/classes/CLASS_UUID" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

---

## 7. Enrollments (admin/instructor: list/get; admin: create/update/delete)

### List enrollments
```bash
curl -X GET "http://localhost:8080/api/enrollments" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Get enrollment by ID
```bash
curl -X GET "http://localhost:8080/api/enrollments/ENROLLMENT_UUID" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Create enrollment (admin)
```bash
curl -X POST "http://localhost:8080/api/enrollments" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"user":{"id":"USER_UUID"},"course":{"id":"COURSE_UUID"}}'
```

### Update enrollment (admin)
```bash
curl -X PUT "http://localhost:8080/api/enrollments/ENROLLMENT_UUID" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"id":"ENROLLMENT_UUID","user":{"id":"USER_UUID"},"course":{"id":"COURSE_UUID"}}'
```

### Delete enrollment (admin)
```bash
curl -X DELETE "http://localhost:8080/api/enrollments/ENROLLMENT_UUID" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

---

## 8. Admissions (admin/instructor: list/get; admin: create/update/delete)

### List admissions
```bash
curl -X GET "http://localhost:8080/api/admissions" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Get admission by ID
```bash
curl -X GET "http://localhost:8080/api/admissions/ADMISSION_UUID" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Create admission (admin)
```bash
curl -X POST "http://localhost:8080/api/admissions" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"course":{"id":"COURSE_UUID"},"user":{"id":"USER_UUID"},"applicantName":"Jane Doe","dateOfBirth":"2010-05-15","gender":"Female","fatherName":"John Doe","motherName":"Jane Sr","email":"jane@example.com","phoneNumber":"+919876543210","address":"123 Street, City","photoUrl":"https://example.com/photo.jpg","classMode":"offline","preferredBatch":"Morning","referralCode":"REF123","status":"pending"}'
```

### Update admission (admin)
```bash
curl -X PUT "http://localhost:8080/api/admissions/ADMISSION_UUID" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"id":"ADMISSION_UUID","course":{"id":"COURSE_UUID"},"applicantName":"Jane Doe","dateOfBirth":"2010-05-15","gender":"Female","fatherName":"John Doe","motherName":"Jane Sr","email":"jane@example.com","phoneNumber":"+919876543210","address":"123 Street, City","classMode":"offline","status":"approved"}'
```

### Delete admission (admin)
```bash
curl -X DELETE "http://localhost:8080/api/admissions/ADMISSION_UUID" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

---

## 9. Payments (admin/instructor: list/get; admin: create/update/delete)

### List payments
```bash
curl -X GET "http://localhost:8080/api/payments" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Get payment by ID
```bash
curl -X GET "http://localhost:8080/api/payments/PAYMENT_UUID" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Create payment (admin)
```bash
curl -X POST "http://localhost:8080/api/payments" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"user":{"id":"USER_UUID"},"course":{"id":"COURSE_UUID"},"amount":2999.00,"status":"pending","transactionReference":"TXN123456"}'
```

### Update payment (admin)
```bash
curl -X PUT "http://localhost:8080/api/payments/PAYMENT_UUID" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"id":"PAYMENT_UUID","user":{"id":"USER_UUID"},"course":{"id":"COURSE_UUID"},"amount":2999.00,"status":"completed","transactionReference":"TXN123456"}'
```

### Delete payment (admin)
```bash
curl -X DELETE "http://localhost:8080/api/payments/PAYMENT_UUID" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

---

## 10. Instructor courses (admin/instructor: list/get; admin: create/update/delete)

### List instructor courses
```bash
curl -X GET "http://localhost:8080/api/instructorcourses" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Get instructor course by ID
```bash
curl -X GET "http://localhost:8080/api/instructorcourses/INSTRUCTOR_COURSE_UUID" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Create instructor course (admin)
```bash
curl -X POST "http://localhost:8080/api/instructorcourses" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"instructor":{"id":"INSTRUCTOR_USER_UUID"},"course":{"id":"COURSE_UUID"}}'
```

### Update instructor course (admin)
```bash
curl -X PUT "http://localhost:8080/api/instructorcourses/INSTRUCTOR_COURSE_UUID" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"id":"INSTRUCTOR_COURSE_UUID","instructor":{"id":"INSTRUCTOR_USER_UUID"},"course":{"id":"COURSE_UUID"}}'
```

### Delete instructor course (admin)
```bash
curl -X DELETE "http://localhost:8080/api/instructorcourses/INSTRUCTOR_COURSE_UUID" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

---

## 11. User roles (admin/instructor: list/get; admin: create/update/delete)

### List user roles
```bash
curl -X GET "http://localhost:8080/api/userroles" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Get user role by ID
```bash
curl -X GET "http://localhost:8080/api/userroles/USER_ROLE_UUID" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Create user role (admin)
```bash
curl -X POST "http://localhost:8080/api/userroles" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"user":{"id":"USER_UUID"},"role":"instructor"}'
```
`role`: `admin` | `instructor` | `student`

### Update user role (admin)
```bash
curl -X PUT "http://localhost:8080/api/userroles/USER_ROLE_UUID" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"id":"USER_ROLE_UUID","user":{"id":"USER_UUID"},"role":"admin"}'
```

### Delete user role (admin)
```bash
curl -X DELETE "http://localhost:8080/api/userroles/USER_ROLE_UUID" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

---

## 12. Site settings (admin/instructor: list/get; admin: create/update/delete)

### List site settings
```bash
curl -X GET "http://localhost:8080/api/sitesettings" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Get site setting by ID
```bash
curl -X GET "http://localhost:8080/api/sitesettings/SETTING_UUID" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Create site setting (admin)
```bash
curl -X POST "http://localhost:8080/api/sitesettings" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"settingKey":"contact_email","settingValue":"contact@sandystereo.com"}'
```

### Update site setting (admin)
```bash
curl -X PUT "http://localhost:8080/api/sitesettings/SETTING_UUID" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"id":"SETTING_UUID","settingKey":"contact_email","settingValue":"updated@sandystereo.com"}'
```

### Delete site setting (admin)
```bash
curl -X DELETE "http://localhost:8080/api/sitesettings/SETTING_UUID" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

---

## Health (no auth)

```bash
curl -X GET "http://localhost:8080/actuator/health"
```

---

**Quick start:**  
1. Run **Login** and copy the `token` from the JSON response.  
2. Replace `YOUR_JWT_TOKEN` in any command above with that token (or set `TOKEN=...` and use the script).  
3. Replace `*_UUID` placeholders with real IDs from list responses.
