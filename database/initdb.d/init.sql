begin;

create extension if not exists pg_stat_statements;
create extension if not exists pg_trgm;

drop table if exists global_versions;
create table global_versions
(
    id          serial primary key,
    label       text not null,
    description text null,
    created_at   timestamp,
    author_data  text null
);

drop index if exists label_index;
create unique index label_index on global_versions (label);

drop table if exists documents;
create table documents
(
    id        serial primary key,
    accession text not null,
    global_versions int[],
    created_at timestamp,
    name      text null
);

drop index if exists created_index;
create index created_index on documents (created_at);

drop index if exists name_index;
-- create index name_index on documents USING GIST ((name) gist_trgm_ops);
create index name_index on documents USING GIN ((name) gin_trgm_ops);

drop index if exists global_versions_index;
create index global_versions_index on documents(global_versions);

-- fill global_versions
insert into global_versions(label, description, created_at, author_data)
select 'v' || i,
       'desc ' || i,
       to_timestamp(0) + i * interval '1 month',
       'dratuti author'
from (select generate_series(1, 2) i) series;

-- fill documents
insert into documents(accession, global_versions, created_at, name)
select 'BTB' || i,
       (select array_agg(id) from global_versions where created_at >= to_timestamp(0) + i * interval '1 day'),
       to_timestamp(0) + i * interval '1 day',
       i || 'document' || to_timestamp(0) + i * interval '1 day'
from (select generate_series(1, 10) i) series;

commit;
