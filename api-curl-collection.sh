#!/bin/bash
# Sandy Stereo Backend - cURL API Collection
# Base URL (change for your environment)
BASE_URL="${BASE_URL:-http://localhost:8080}"
# After login, set this or paste token: export TOKEN="<your-jwt-token>"
AUTH_HEADER="${TOKEN:+Authorization: Bearer $TOKEN}"

# =============================================================================
# 1. AUTH (no token required)
# =============================================================================

# 1.1 Register
curl -s -X POST "$BASE_URL/api/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "student@example.com",
    "password": "yourSecurePassword",
    "roles": ["student"]
  }'
# roles: ["admin"] | ["instructor"] | ["student"] or combination

# 1.2 Login
curl -s -X POST "$BASE_URL/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "student@example.com",
    "password": "yourSecurePassword"
  }'
# Save the "token" from response; use: export TOKEN="<token>"

# =============================================================================
# 2. USERS (admin only)
# =============================================================================

# 2.1 List users
curl -s -X GET "$BASE_URL/api/users" \
  -H "Authorization: Bearer $TOKEN"

# 2.2 Get user by ID
curl -s -X GET "$BASE_URL/api/users/{id}" \
  -H "Authorization: Bearer $TOKEN"

# 2.3 Delete user
curl -s -X DELETE "$BASE_URL/api/users/{id}" \
  -H "Authorization: Bearer $TOKEN"

# =============================================================================
# 3. PROFILES (admin: full; instructor: list/get)
# =============================================================================

# 3.1 List profiles
curl -s -X GET "$BASE_URL/api/profiles" \
  -H "Authorization: Bearer $TOKEN"

# 3.2 Get profile by ID
curl -s -X GET "$BASE_URL/api/profiles/{id}" \
  -H "Authorization: Bearer $TOKEN"

# 3.3 Create profile (admin)
curl -s -X POST "$BASE_URL/api/profiles" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "user": { "id": "user-uuid-here" },
    "fullName": "John Doe",
    "avatarUrl": "https://example.com/avatar.png"
  }'

# 3.4 Update profile (admin)
curl -s -X PUT "$BASE_URL/api/profiles/{id}" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "profile-uuid",
    "user": { "id": "user-uuid" },
    "fullName": "John Doe Updated",
    "avatarUrl": "https://example.com/avatar2.png"
  }'

# 3.5 Delete profile (admin)
curl -s -X DELETE "$BASE_URL/api/profiles/{id}" \
  -H "Authorization: Bearer $TOKEN"

# =============================================================================
# 4. COURSES (public: list/get; admin: create/update/delete)
# =============================================================================

# 4.1 List courses (no auth for list/get)
curl -s -X GET "$BASE_URL/api/courses"

# 4.2 Get course by ID
curl -s -X GET "$BASE_URL/api/courses/{id}"

# 4.3 Create course (admin)
curl -s -X POST "$BASE_URL/api/courses" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Guitar",
    "description": "Learn acoustic and electric guitar.",
    "imageUrl": "https://example.com/guitar.jpg",
    "level": "Intermediate",
    "duration": "12 weeks",
    "isActive": true,
    "googleFormLink": "https://forms.google.com/...",
    "whatsappNumber": "+91...",
    "upiPrice": 2999.00
  }'
# level: Beginner | Intermediate | Advanced

# 4.4 Update course (admin)
curl -s -X PUT "$BASE_URL/api/courses/{id}" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "course-uuid",
    "title": "Guitar Updated",
    "description": "Updated description",
    "level": "Intermediate",
    "isActive": true
  }'

# 4.5 Delete course (admin)
curl -s -X DELETE "$BASE_URL/api/courses/{id}" \
  -H "Authorization: Bearer $TOKEN"

# =============================================================================
# 5. EVENTS (public: list/get; admin: create/update/delete)
# =============================================================================

# 5.1 List events
curl -s -X GET "$BASE_URL/api/events"

# 5.2 Get event by ID
curl -s -X GET "$BASE_URL/api/events/{id}"

# 5.3 Create event (admin)
curl -s -X POST "$BASE_URL/api/events" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Annual Concert",
    "description": "Year-end performance",
    "eventDate": "2025-12-31T18:00:00+05:30",
    "bannerUrl": "https://example.com/banner.jpg",
    "googleFormLink": "https://forms.google.com/...",
    "isActive": true
  }'

# 5.4 Update event (admin)
curl -s -X PUT "$BASE_URL/api/events/{id}" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "event-uuid",
    "name": "Annual Concert Updated",
    "eventDate": "2025-12-31T18:00:00+05:30",
    "isActive": true
  }'

# 5.5 Delete event (admin)
curl -s -X DELETE "$BASE_URL/api/events/{id}" \
  -H "Authorization: Bearer $TOKEN"

# =============================================================================
# 6. CLASSES (admin/instructor: list/get; admin: create/update/delete)
# =============================================================================

# 6.1 List classes
curl -s -X GET "$BASE_URL/api/classes" \
  -H "Authorization: Bearer $TOKEN"

# 6.2 Get class by ID
curl -s -X GET "$BASE_URL/api/classes/{id}" \
  -H "Authorization: Bearer $TOKEN"

# 6.3 Create class (admin)
curl -s -X POST "$BASE_URL/api/classes" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "course": { "id": "course-uuid" },
    "instructor": { "id": "instructor-user-uuid" },
    "title": "Week 1 - Basics",
    "scheduledAt": "2025-03-01T10:00:00+05:30",
    "meetLink": "https://meet.google.com/..."
  }'

# 6.4 Update class (admin)
curl -s -X PUT "$BASE_URL/api/classes/{id}" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "class-uuid",
    "course": { "id": "course-uuid" },
    "title": "Week 1 - Basics Updated",
    "scheduledAt": "2025-03-01T10:00:00+05:30",
    "meetLink": "https://meet.google.com/..."
  }'

# 6.5 Delete class (admin)
curl -s -X DELETE "$BASE_URL/api/classes/{id}" \
  -H "Authorization: Bearer $TOKEN"

# =============================================================================
# 7. ENROLLMENTS (admin/instructor: list/get; admin: create/update/delete)
# =============================================================================

# 7.1 List enrollments
curl -s -X GET "$BASE_URL/api/enrollments" \
  -H "Authorization: Bearer $TOKEN"

# 7.2 Get enrollment by ID
curl -s -X GET "$BASE_URL/api/enrollments/{id}" \
  -H "Authorization: Bearer $TOKEN"

# 7.3 Create enrollment (admin)
curl -s -X POST "$BASE_URL/api/enrollments" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "user": { "id": "user-uuid" },
    "course": { "id": "course-uuid" }
  }'

# 7.4 Update enrollment (admin)
curl -s -X PUT "$BASE_URL/api/enrollments/{id}" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "enrollment-uuid",
    "user": { "id": "user-uuid" },
    "course": { "id": "course-uuid" }
  }'

# 7.5 Delete enrollment (admin)
curl -s -X DELETE "$BASE_URL/api/enrollments/{id}" \
  -H "Authorization: Bearer $TOKEN"

# =============================================================================
# 8. ADMISSIONS (admin/instructor: list/get; admin: create/update/delete)
# =============================================================================

# 8.1 List admissions
curl -s -X GET "$BASE_URL/api/admissions" \
  -H "Authorization: Bearer $TOKEN"

# 8.2 Get admission by ID
curl -s -X GET "$BASE_URL/api/admissions/{id}" \
  -H "Authorization: Bearer $TOKEN"

# 8.3 Create admission (admin)
curl -s -X POST "$BASE_URL/api/admissions" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "course": { "id": "course-uuid" },
    "user": { "id": "user-uuid" },
    "applicantName": "Jane Doe",
    "dateOfBirth": "2010-05-15",
    "gender": "Female",
    "fatherName": "John Doe",
    "motherName": "Jane Sr",
    "email": "jane@example.com",
    "phoneNumber": "+919876543210",
    "address": "123 Street, City",
    "photoUrl": "https://example.com/photo.jpg",
    "classMode": "offline",
    "preferredBatch": "Morning",
    "referralCode": "REF123",
    "status": "pending"
  }'

# 8.4 Update admission (admin)
curl -s -X PUT "$BASE_URL/api/admissions/{id}" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "admission-uuid",
    "course": { "id": "course-uuid" },
    "applicantName": "Jane Doe",
    "dateOfBirth": "2010-05-15",
    "gender": "Female",
    "fatherName": "John Doe",
    "motherName": "Jane Sr",
    "email": "jane@example.com",
    "phoneNumber": "+919876543210",
    "address": "123 Street, City",
    "classMode": "offline",
    "status": "approved"
  }'

# 8.5 Delete admission (admin)
curl -s -X DELETE "$BASE_URL/api/admissions/{id}" \
  -H "Authorization: Bearer $TOKEN"

# =============================================================================
# 9. PAYMENTS (admin/instructor: list/get; admin: create/update/delete)
# =============================================================================

# 9.1 List payments
curl -s -X GET "$BASE_URL/api/payments" \
  -H "Authorization: Bearer $TOKEN"

# 9.2 Get payment by ID
curl -s -X GET "$BASE_URL/api/payments/{id}" \
  -H "Authorization: Bearer $TOKEN"

# 9.3 Create payment (admin)
curl -s -X POST "$BASE_URL/api/payments" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "user": { "id": "user-uuid" },
    "course": { "id": "course-uuid" },
    "amount": 2999.00,
    "status": "pending",
    "transactionReference": "TXN123456"
  }'

# 9.4 Update payment (admin)
curl -s -X PUT "$BASE_URL/api/payments/{id}" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "payment-uuid",
    "user": { "id": "user-uuid" },
    "course": { "id": "course-uuid" },
    "amount": 2999.00,
    "status": "completed",
    "transactionReference": "TXN123456"
  }'

# 9.5 Delete payment (admin)
curl -s -X DELETE "$BASE_URL/api/payments/{id}" \
  -H "Authorization: Bearer $TOKEN"

# =============================================================================
# 10. INSTRUCTOR COURSES (admin/instructor: list/get; admin: create/update/delete)
# =============================================================================

# 10.1 List instructor courses
curl -s -X GET "$BASE_URL/api/instructorcourses" \
  -H "Authorization: Bearer $TOKEN"

# 10.2 Get instructor course by ID
curl -s -X GET "$BASE_URL/api/instructorcourses/{id}" \
  -H "Authorization: Bearer $TOKEN"

# 10.3 Create instructor course (admin)
curl -s -X POST "$BASE_URL/api/instructorcourses" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "instructor": { "id": "instructor-user-uuid" },
    "course": { "id": "course-uuid" }
  }'

# 10.4 Update instructor course (admin)
curl -s -X PUT "$BASE_URL/api/instructorcourses/{id}" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "instructor-course-uuid",
    "instructor": { "id": "instructor-user-uuid" },
    "course": { "id": "course-uuid" }
  }'

# 10.5 Delete instructor course (admin)
curl -s -X DELETE "$BASE_URL/api/instructorcourses/{id}" \
  -H "Authorization: Bearer $TOKEN"

# =============================================================================
# 11. USER ROLES (admin/instructor: list/get; admin: create/update/delete)
# =============================================================================

# 11.1 List user roles
curl -s -X GET "$BASE_URL/api/userroles" \
  -H "Authorization: Bearer $TOKEN"

# 11.2 Get user role by ID
curl -s -X GET "$BASE_URL/api/userroles/{id}" \
  -H "Authorization: Bearer $TOKEN"

# 11.3 Create user role (admin)
curl -s -X POST "$BASE_URL/api/userroles" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "user": { "id": "user-uuid" },
    "role": "instructor"
  }'
# role: admin | instructor | student

# 11.4 Update user role (admin)
curl -s -X PUT "$BASE_URL/api/userroles/{id}" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "user-role-uuid",
    "user": { "id": "user-uuid" },
    "role": "admin"
  }'

# 11.5 Delete user role (admin)
curl -s -X DELETE "$BASE_URL/api/userroles/{id}" \
  -H "Authorization: Bearer $TOKEN"

# =============================================================================
# 12. SITE SETTINGS (admin/instructor: list/get; admin: create/update/delete)
# =============================================================================

# 12.1 List site settings
curl -s -X GET "$BASE_URL/api/sitesettings" \
  -H "Authorization: Bearer $TOKEN"

# 12.2 Get site setting by ID
curl -s -X GET "$BASE_URL/api/sitesettings/{id}" \
  -H "Authorization: Bearer $TOKEN"

# 12.3 Create site setting (admin)
curl -s -X POST "$BASE_URL/api/sitesettings" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "settingKey": "contact_email",
    "settingValue": "contact@sandystereo.com"
  }'

# 12.4 Update site setting (admin)
curl -s -X PUT "$BASE_URL/api/sitesettings/{id}" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "setting-uuid",
    "settingKey": "contact_email",
    "settingValue": "updated@sandystereo.com"
  }'

# 12.5 Delete site setting (admin)
curl -s -X DELETE "$BASE_URL/api/sitesettings/{id}" \
  -H "Authorization: Bearer $TOKEN"

# =============================================================================
# HEALTH (no auth)
# =============================================================================
curl -s -X GET "$BASE_URL/actuator/health"
