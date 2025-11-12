CREATE TABLE IF NOT EXISTS public.roles
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE CHECK (name IN ('USER', 'ADMIN'))
);

INSERT INTO public.roles (name)
SELECT 'USER'
WHERE NOT EXISTS (SELECT 1 FROM public.roles WHERE name = 'USER');
INSERT INTO public.roles (name)
SELECT 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM public.roles WHERE name = 'ADMIN');
INSERT INTO public.roles (name)
SELECT 'GUEST'
WHERE NOT EXISTS (SELECT 1 FROM public.roles WHERE name = 'GUEST');

CREATE TABLE IF NOT EXISTS public.users
(
    id         VARCHAR(255) PRIMARY KEY, --PK always created with index
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    birthday   DATE         NOT NULL,
    locked     BOOLEAN               DEFAULT FALSE,
    created_at TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS public.posts
(
    id         BIGSERIAL PRIMARY KEY,
    author_id  VARCHAR(255) NOT NULL REFERENCES public.users (id) ON DELETE CASCADE,
    text       TEXT,
    photo_url  VARCHAR(255),
    created_at TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS public.user_roles
(
    user_id VARCHAR(255) NOT NULL REFERENCES public.users (id) ON DELETE CASCADE,
    role_id BIGSERIAL      NOT NULL REFERENCES public.roles (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);