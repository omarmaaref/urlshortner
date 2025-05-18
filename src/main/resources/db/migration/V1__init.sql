
    create sequence auto_incremented_index_seq start with 20000 increment by 50;

    create table auto_incremented_index (
        id bigint not null,
        index bigint,
        primary key (id)
    );

    INSERT INTO public.auto_incremented_index (id, index)
    VALUES (1, 238328);

    create table urls (
        view_count bigint not null,
        id uuid not null,
        code varchar(255) not null unique,
        full_url varchar(255) not null,
        primary key (id)
    );

