-- Sandy's Stereo - FULL RESET + REBUILD (Neon Compatible)
DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TYPE app_role AS ENUM ('admin', 'instructor', 'student');
CREATE TYPE course_level AS ENUM ('Beginner', 'Intermediate', 'Advanced');

CREATE OR REPLACE FUNCTION public.update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = now();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TABLE public.users (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  email TEXT UNIQUE NOT NULL,
  password_hash TEXT NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE public.profiles (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL UNIQUE REFERENCES public.users(id) ON DELETE CASCADE,
  full_name TEXT,
  avatar_url TEXT,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TRIGGER set_profiles_updated_at BEFORE UPDATE ON public.profiles FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();
CREATE TABLE public.user_roles (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL REFERENCES public.users(id) ON DELETE CASCADE,
  role app_role NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  UNIQUE (user_id, role)
);
CREATE TABLE public.courses (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  title TEXT NOT NULL,
  description TEXT NOT NULL,
  image_url TEXT,
  level course_level NOT NULL DEFAULT 'Beginner',
  duration TEXT,
  is_active BOOLEAN NOT NULL DEFAULT true,
  google_form_link TEXT,
  whatsapp_number TEXT,
  upi_price NUMERIC(10,2),
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TRIGGER set_courses_updated_at BEFORE UPDATE ON public.courses FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();
CREATE TABLE public.events (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name TEXT NOT NULL,
  description TEXT,
  event_date TIMESTAMPTZ NOT NULL,
  banner_url TEXT,
  google_form_link TEXT,
  is_active BOOLEAN NOT NULL DEFAULT true,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TRIGGER set_events_updated_at BEFORE UPDATE ON public.events FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();
CREATE TABLE public.enrollments (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL REFERENCES public.users(id) ON DELETE CASCADE,
  course_id UUID NOT NULL REFERENCES public.courses(id) ON DELETE CASCADE,
  enrolled_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  UNIQUE(user_id, course_id)
);
CREATE TABLE public.admissions (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  course_id UUID NOT NULL REFERENCES public.courses(id) ON DELETE CASCADE,
  user_id UUID REFERENCES public.users(id) ON DELETE SET NULL,
  applicant_name TEXT NOT NULL,
  date_of_birth DATE NOT NULL,
  gender TEXT NOT NULL,
  father_name TEXT NOT NULL,
  mother_name TEXT NOT NULL,
  email TEXT NOT NULL,
  phone_number TEXT NOT NULL,
  address TEXT NOT NULL,
  photo_url TEXT,
  class_mode TEXT NOT NULL DEFAULT 'offline',
  preferred_batch TEXT,
  referral_code TEXT,
  status TEXT NOT NULL DEFAULT 'pending',
  created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE public.payments (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL REFERENCES public.users(id) ON DELETE CASCADE,
  course_id UUID NOT NULL REFERENCES public.courses(id) ON DELETE CASCADE,
  amount NUMERIC(10,2) NOT NULL,
  status TEXT NOT NULL DEFAULT 'pending',
  transaction_reference TEXT,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE public.classes (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  course_id UUID NOT NULL REFERENCES public.courses(id) ON DELETE CASCADE,
  instructor_id UUID REFERENCES public.users(id) ON DELETE SET NULL,
  title TEXT NOT NULL,
  scheduled_at TIMESTAMPTZ NOT NULL,
  meet_link TEXT,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE public.instructor_courses (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  instructor_id UUID NOT NULL REFERENCES public.users(id) ON DELETE CASCADE,
  course_id UUID NOT NULL REFERENCES public.courses(id) ON DELETE CASCADE,
  assigned_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  UNIQUE(instructor_id, course_id)
);
CREATE TABLE public.site_settings (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  setting_key TEXT NOT NULL UNIQUE,
  setting_value TEXT,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TRIGGER set_site_settings_updated_at BEFORE UPDATE ON public.site_settings FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();

INSERT INTO public.courses (id, title, description, level, upi_price) VALUES
('a24a36ab-f429-4e15-9041-7b0a192a55c3', 'Guitar', 'Learn acoustic and electric guitar from basics to performance level.', 'Intermediate', 2999.00),
('c87b0c3c-d7ad-4a94-b4fe-084316456470', 'Piano', 'Master piano fundamentals including scales and chords.', 'Intermediate', 3499.00),
('ee4948c9-129a-4549-a302-789069c81c74', 'Drums', 'Develop rhythm, timing, and coordination.', 'Intermediate', 2799.00),
('a9986581-9683-4513-87e3-01afcac2d0a6', 'Flute', 'Learn breath control and fingering techniques.', 'Intermediate', 2499.00),
('4818b6f1-bb93-4981-ace1-eebb7d7657a0', 'Ukulele', 'Basic chords and popular songs.', 'Beginner', 1999.00),
('d9f0547a-682d-4460-81c5-d679d704379a', 'Tabla', 'Traditional tabla training and rhythm patterns.', 'Intermediate', 2999.00),
('d1317d7e-3d23-4e73-a48f-3a2271073540', 'Vocals (Hindustani Classical)', 'Classical vocal training and ragas.', 'Advanced', 3999.00),
('84b7a5d4-689f-4f73-8c94-cb86bb726148', 'Cajon (Clap Box)', 'Cajon rhythms and live performance skills.', 'Beginner', 2299.00),
('27c17cf3-5e95-4699-87a7-ad6a1017786d', 'Harmonium', 'Harmonium lessons and vocal accompaniment.', 'Intermediate', 2699.00);
INSERT INTO public.events (id, name, description, event_date, google_form_link) VALUES
('a4f4de18-4365-4035-9811-0f4fc7addbd8', 'Annual Music Festival 2026', 'Grand annual music festival featuring students and guest artists.', '2026-03-15 12:30:00+00', 'https://forms.google.com'),
('d558dd59-a215-43e4-a4aa-3f1a1e22bbeb', 'Classical Music Workshop', 'Workshop on Hindustani classical music fundamentals.', '2026-02-28 04:30:00+00', 'https://forms.google.com');
INSERT INTO public.site_settings (setting_key, setting_value) VALUES
('global_whatsapp_number', '+919876543210'),
('upi_id', 'musicinstitute@upi'),
('hero_banner_url', ''),
('about_text', 'Welcome to our Music Institute - where passion meets excellence.');
