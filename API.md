API overview
============

## Contents

- [Methods](#methods)
  - [Users](#users)
  - [Discounts](#discounts)
- [Entities](#entities)
  - [User](#user)
  - [Discount](#discount)

___

## Methods

### Users

#### Login:

    POST /api/v1/login

Form data:

| Field         | Description                       | Optional   |
| ------------- | --------------------------------- | ---------- |
| `username`    | The username of the user          | yes        |
| `password`    | The password of the user          | yes        |

Returns the status true/false.
{ status: true/false }

#### Logout:

    POST /api/v1/logout

Returns the status true/false.
{ status: true/false }

### Discounts

#### List all of the discounts:

    GET /api/v1/discounts

Query parameters:

| Field      | Description                               | Optional   |
| ---------- | ----------------------------------------- | ---------- |
| `keywords` | Keywords used to search discount          | yes        |
| `offset`   | The start number of requested records     | yes        |
| `limit`    | Number of requested records               | yes        |
| `sort`     | The sorting criteria of requested records | yes        |
|            | 'hot': records have the highest vote      |            |
|            | 'new': records have just created          |            |

#### Get a discount:
    GET /api/v1/discounts/:id

Returns the [Discount](#discount).

#### Posting a new discount:
    POST /api/v1/discounts

Form data:

| Field         | Description                       | Optional |
| ------------- | --------------------------------- | -------- |
| `id`          | The ID of the discount            | no       |
| `title`       | The title or name of the discount | no       |
| `price`       | The price of the discount         | no       |
| `vote_temp`   | The number of vote of the discount| no       |
| `link`        | The link to the discount          | no       |
| `category`    | The category of the discount      | no       |
| `valid_from`  | Date the discount start           | no       |
| `valid_until` | Date the discount end             | no       |
| `description` | Detail of the discount            | yes      |
| `image`       | A base64 encoded image            | yes      |
| `creator`     | The name of the discount's creator| no       |

Returns the new [Discount](#discount).

#### Updating a discount:
    PUT /api/v1/discounts/:id

Form data:

| Field         | Description                       | Optional |
| ------------- | --------------------------------- | -------- |
| `id`          | The ID of the discount            | no       |
| `title`       | The title or name of the discount | no       |
| `price`       | The price of the discount         | no       |
| `vote_temp`   | The number of vote of the discount| no       |
| `link`        | The link to the discount          | no       |
| `category`    | The category of the discount      | no       |
| `valid_from`  | Date the discount start           | no       |
| `valid_until` | Date the discount end             | no       |
| `description` | Detail of the discount            | yes      |
| `image`       | A base64 encoded image            | yes      |
| `creator`     | The name of the discount's creator| no       |

Returns the updated [Discount](#discount).

#### Deleting a discount:
    DELETE /api/v1/discounts/:id

Returns the status true/false.
{ status: true/false }

___

## Entities

### User

| Attribute                | Description                                                                        | Nullable |
| ------------------------ | ---------------------------------------------------------------------------------- | -------- |
| `id`                     | The ID of the user                                                                 | no       |
| `username`               | The username of the user                                                           | no       |
| `password`               | The user password                                                                  | no       |
| `created_at`             | The time the user was created                                                      | no       |
| `updated_at`             | The time the user was last updated                                                 | no       |

### Discount

| Attribute                | Description                            | Nullable | Type    |
| ------------------------ | -------------------------------------- | -------- | ------- |
| `id`                     | The ID of the discount                 | no       | int     |
| `title`                  | The title or name of the discount      | no       | string  |
| `price`                  | The price of the discount              | no       | double  |
| `vote_temp`              | The number of vote of the discount     | no       | int     |
| `link`                   | The link to the discount               | no       | string  |
| `category`               | The category of the discount           | no       | string  |
| `valid_from`             | Date the discount start                | no       | date    |
| `valid_until`            | Date the discount end                  | no       | date    |
| `description`            | Detail of the discount                 | yes      | string  |
| `image`                  | A base64 encoded image                 | yes      |         |
| `creator`                | The name of the discount's creator     | no       | string  |
| `created_at`             | The time the discount was created      | no       | date    |
| `updated_at`             | The time the discount was last updated | no       | date    |
