DO $$
DECLARE
  v_user_id UUID;
  v_course_id UUID;
BEGIN
  -- Check if user exists
  SELECT id INTO v_user_id FROM public.users WHERE email = 'souravkalal991@gmail.com';
  
  IF v_user_id IS NULL THEN
    -- Create user (password: 'password')
    INSERT INTO public.users (email, password_hash) 
    VALUES ('souravkalal991@gmail.com', '$2a$10$wT0E0lMw5F7U1P5V0E6uH.Jg.j8M3uQ2yRkL7Q/Cj6s0n') 
    RETURNING id INTO v_user_id;

    INSERT INTO public.profiles (user_id, full_name) 
    VALUES (v_user_id, 'Sourav Kalal');

    INSERT INTO public.user_roles (user_id, role) 
    VALUES (v_user_id, 'student');
  END IF;

  -- Get Tabla Course ID
  SELECT id INTO v_course_id FROM public.courses WHERE title = 'Tabla' LIMIT 1;

  -- Create Enrollment
  IF v_course_id IS NOT NULL THEN
    INSERT INTO public.enrollments (user_id, course_id) 
    VALUES (v_user_id, v_course_id) 
    ON CONFLICT (user_id, course_id) DO NOTHING;
  END IF;
END $$;
