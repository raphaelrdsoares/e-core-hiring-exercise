// Copy the code below and paste it in https://dbdiagram.io/d to see the ER Diagram Model

Table "users" {
  "id" VARCHAR [pk, not null]
  "first_name" VARCHAR [not null]
  "last_name" VARCHAR [not null]
  "display_name" VARCHAR [not null]
  "avatar_url" VARCHAR
  "location" VARCHAR
  "created_at" TIMESTAMP [not null]
  "updated_at" TIMESTAMP [not null]
}

Table "teams" {
  "id" VARCHAR [pk, not null]
  "name" VARCHAR [not null]
  "team_lead_id" VARCHAR
  "created_at" TIMESTAMP [not null]
  "updated_at" TIMESTAMP [not null]
}

Table "team_member" {
  "user_id" VARCHAR [not null]
  "team_id" VARCHAR [not null]
  "created_at" TIMESTAMP [not null]
  "updated_at" TIMESTAMP [not null]

Indexes {
  (user_id, team_id) [pk]
}
}

Table "roles" {
  "id" VARCHAR [pk, not null]
  "code" VARCHAR [not null]
  "name" VARCHAR [not null]
  "is_default" BOOLEAN [not null]
  "created_at" TIMESTAMP [not null]
  "updated_at" TIMESTAMP [not null]
}

Table "memberships" {
  "id" VARCHAR [pk, not null]
  "role_code" VARCHAR [not null]
  "user_id" VARCHAR [not null]
  "team_id" VARCHAR [not null]
  "created_at" TIMESTAMP [not null]
  "updated_at" TIMESTAMP [not null]
}

Ref:"users"."id" < "team_member"."user_id"

Ref:"teams"."id" < "team_member"."team_id"

Ref:"users"."id" < "memberships"."user_id"

Ref:"teams"."id" < "memberships"."team_id"

Ref:"roles"."code" < "memberships"."role_code"
