/* 
  V3 - Move admission personal details to profiles 
*/

ALTER TABLE public.profiles
ADD COLUMN date_of_birth DATE,
ADD COLUMN gender TEXT,
ADD COLUMN father_name TEXT,
ADD COLUMN mother_name TEXT,
ADD COLUMN phone_number TEXT,
ADD COLUMN address TEXT;

ALTER TABLE public.admissions
ALTER COLUMN applicant_name DROP NOT NULL,
ALTER COLUMN date_of_birth DROP NOT NULL,
ALTER COLUMN gender DROP NOT NULL,
ALTER COLUMN father_name DROP NOT NULL,
ALTER COLUMN mother_name DROP NOT NULL,
ALTER COLUMN email DROP NOT NULL,
ALTER COLUMN phone_number DROP NOT NULL,
ALTER COLUMN address DROP NOT NULL;
