create extension if not exists pg_stat_statements;
create extension if not exists pg_trgm;

drop table if exists global_versions;
create table global_versions
(
    id          serial primary key,
    label       text not null,
    description text null,
    createdAt   timestamp,
    authorData  text null
);

drop index if exists label_index;
create unique index label_index on global_versions (label);

drop table if exists documents;
create table documents
(
    id        serial primary key,
    accession text not null,
    createdAt timestamp,
    name      text null
);

drop index if exists created_index;
create index concurrently created_index on documents (createdAt);

drop index if exists name_index;
-- create index name_index on documents USING GIST ((name) gist_trgm_ops);
create index name_index on documents USING GIN ((name) gin_trgm_ops);

drop table if exists documents_global_versions;
create table documents_global_versions(
                                          global_version_id int not null,
                                          document_id int not null,
                                          primary key (global_version_id, document_id)
);

-- fill global_versions
insert into global_versions(label, description, createdAt, authorData)
select 'v' || i,
       'desc ' || i,
       to_timestamp(0) + i * interval '1 month',
       'dratuti author'
from (select generate_series(1, 100) i) series;

-- fill documents
insert into documents(accession, createdat, name)
select 'BTB' || i,
       to_timestamp(0) + i * interval '5 minute',
       i || 'document' || to_timestamp(0) + i * interval '5 minute'
from (select generate_series(1, 1000000) i) series;

--fill documents_global_versions
insert into documents_global_versions(global_version_id, document_id)
    (select g.id, d.id from global_versions g join documents d on g.createdAt >= d.createdAt);

