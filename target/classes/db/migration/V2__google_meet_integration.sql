-- Sandy's Stereo - Google Meet Integration Migration

CREATE TABLE IF NOT EXISTS public.instructor_google_credentials (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL UNIQUE REFERENCES public.users(id) ON DELETE CASCADE,
  access_token TEXT NOT NULL,
  refresh_token TEXT NOT NULL,
  expires_at TIMESTAMPTZ NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_trigger WHERE tgname = 'set_instructor_google_credentials_updated_at') THEN
        CREATE TRIGGER set_instructor_google_credentials_updated_at 
        BEFORE UPDATE ON public.instructor_google_credentials 
        FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();
    END IF;
END $$;

ALTER TABLE public.classes ADD COLUMN IF NOT EXISTS google_event_id TEXT;
