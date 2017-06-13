API overview
============

## Contents

- [Methods](#methods)
  - [Users](#users)
  - [Discounts](#discounts)
  - [Categories](#categories)
- [Entities](#entities)
  - [User](#user)
  - [Discount](#discount)
  - [Category](#category)

___

## Methods

### Users

#### Fetching an user:

    GET /api/v1/users/:id

Returns an [User](#user).

#### Getting the current user:

    GET /api/v1/users/verify_credentials

Returns the authenticated user's [User](#user).

#### Updating the current user:

    PATCH /api/v1/users/update_credentials

Form data:

| Field          | Description                                                                                                                            | Optional   |
| -------------- | -------------------------------------------------------------------------------------------------------------------------------------- | ---------- |
| `display_name` | The name to display in the user's profile                                                                                              | yes        |
| `avatar`       | A base64 encoded image to display as the user's avatar (e.g. `data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUoAAADrCAYAAAA...`)       | yes        |

### Discounts

#### List all of the discounts:

    GET /api/v1/discounts

Query parameters:

| Field             | Description                                                   | Optional   |
| ----------------- | ------------------------------------------------------------- | ---------- |
| `max_id`          | Get a list of blocks with ID less than this value             | yes        |
| `since_id`        | Get a list of blocks with ID greater than this value          | yes        |
| `limit`           | Maximum number of blocks to get (Default 40, Max 80)          | yes        |

#### Get single discount:
    GET /api/v1/discounts/:id

Returns the [Discount](#discount).

#### Posting a new discount:
    POST /api/v1/discounts

Form data:

| Field         | Description                       | Optional   |
| --------------| ----------------------------------| ---------- |
| `title`       | The title or name of the discount | no         |
| `valid_from`  | Date the discount start           | no         |
| `valid_until` | Date the discount end             | no         |
| `description` | Detail of the discount            | yes        |
| `image`       | A base64 encoded image            | yes        |

Returns the new [Discount](#discount).

___

## Entities

### User

| Attribute                | Description                                                                        | Nullable |
| ------------------------ | ---------------------------------------------------------------------------------- | -------- |
| `id`                     | The ID of the user                                                                 | no       |
| `username`               | The username of the user                                                           | no       |
| `email`                  | The email address of the user                                                      | no       |
| `first_name`             | The user first name                                                                | no       |
| `last_name`              | The user second name                                                               | no       |
| `password`               | The user password                                                                  | no       |
| `address`                | The user address                                                                   | yes      |
| `enabled`                | The user status                                                                    | no       |
| `role`                   | The user role                                                                      | no       |
| `avatar`                 | The user avatar image                                                              | yes      |
| `created_at`             | The time the user was created                                                      | no       |
| `updated_at`             | The time the user was last updated                                                 | no       |

### Discount

| Attribute                | Description                            | Nullable |
| ------------------------ | ---------------------------------------| -------- |
| `id`                     | The ID of the discount                 | no       |
| `title`                  | The title or name of the discount      | no       |
| `valid_from`             | Date the discount start                | no       |
| `valid_until`            | Date the discount end                  | no       |
| `description`            | Detail of the discount                 | yes      |
| `image`                  | A base64 encoded image                 | yes      |
| `created_at`             | The time the discount was created      | no       |
| `updated_at`             | The time the discount was last updated | no       |
