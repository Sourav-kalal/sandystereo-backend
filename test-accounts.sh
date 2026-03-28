#!/bin/bash
BASE_URL="${BASE_URL:-http://localhost:8080}"

echo "========================================"
echo "Checking API Health"
echo "========================================"
curl -s -o /dev/null -w "Health Status: %{http_code}\n" "$BASE_URL/actuator/health"

echo ""
echo "========================================"
echo "1. Creating Admin Account"
echo "========================================"
ADMIN_HTTP=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/api/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin2@example.com",
    "password": "securePassword123",
    "roles": ["admin"]
  }')
echo "Register Admin HTTP Code: $ADMIN_HTTP"

echo ""
echo "========================================"
echo "2. Creating Instructor Account"
echo "========================================"
INSTRUCTOR_HTTP=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/api/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "instructor2@example.com",
    "password": "securePassword123",
    "roles": ["instructor"]
  }')
echo "Register Instructor HTTP Code: $INSTRUCTOR_HTTP"

echo ""
echo "========================================"
echo "3. Creating Student Account"
echo "========================================"
STUDENT_HTTP=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/api/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "student3@example.com",
    "password": "securePassword123",
    "roles": ["student"]
  }')
echo "Register Student HTTP Code: $STUDENT_HTTP"

echo ""
echo "========================================"
echo "4. Testing Login for Admin"
echo "========================================"
ADMIN_LOGIN=$(curl -s -w "\nHTTP Code: %{http_code}\n" -X POST "$BASE_URL/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin2@example.com",
    "password": "securePassword123"
  }')
echo "$ADMIN_LOGIN" | grep "HTTP Code"

echo ""
echo "========================================"
echo "5. Testing Login for Instructor"
echo "========================================"
INSTRUCTOR_LOGIN=$(curl -s -w "\nHTTP Code: %{http_code}\n" -X POST "$BASE_URL/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "instructor2@example.com",
    "password": "securePassword123"
  }')
echo "$INSTRUCTOR_LOGIN" | grep "HTTP Code"

echo ""
echo "========================================"
echo "6. Testing Login for Student"
echo "========================================"
STUDENT_LOGIN=$(curl -s -w "\nHTTP Code: %{http_code}\n" -X POST "$BASE_URL/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "student3@example.com",
    "password": "securePassword123"
  }')
echo "$STUDENT_LOGIN" | grep "HTTP Code"

echo ""
echo "Done testing accounts."
