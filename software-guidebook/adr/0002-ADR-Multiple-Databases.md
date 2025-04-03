# ADR 002: Use of Two Databases for Security Reasons

## Status
Accepted

## Context
The traveling application needs to handle sensitive data, including personal information, payment details, and travel itineraries. To ensure enhanced security and minimize risk in case of a breach, we are considering using two separate databases. One will store general application data, and the other will store sensitive data. This approach aims to isolate the sensitive data to add an additional layer of security.

## Decision
We will implement two databases: 
The first database will store non-sensitive user data, such as user preferences, general travel information, and non-sensitive logs. The second database will store sensitive information like personal details, payment data, travel documents, and other high-risk data. By separating these two types of data, we can implement more granular security controls on the sensitive database, improve the overall security posture of the application by limiting access to sensitive data, and apply stricter encryption policies for the sensitive database.

For the sensitive database, encryption at rest will be enforced using strong encryption algorithms, access control will be finely tuned, and data masking will be applied where needed. For the non-sensitive database, the encryption will be less stringent, but access will still be restricted.

## Consequences
Managing two databases will introduce additional operational complexity, including database synchronization, backup management, and maintenance overhead. There may also be performance impacts due to the need to access data across two databases, which could introduce latency. However, with careful design and optimization, these impacts can be minimized.

By isolating sensitive data into a separate database, we minimize the risk of exposure in case of a database breach. If an attacker compromises the non-sensitive database, they will still not have access to the sensitive data. However, maintaining two databases may incur additional costs related to infrastructure, backup, and data synchronization.

## Considered Options

| Option                     | Single Database | Two Separate Databases |
| -------------------------- | --------------- | ---------------------- |
| **Operational Complexity** | +               | -                      |
| **Security**               | -               | ++                     |
| **Scalability**            | +               | +                      |
| **Maintainability**        | 0               | -                      |

Legend:
- ++ : Excellent fit / Strong advantage
- + : Good fit / Advantage
- 0 : Neutral / Average
- - : Poor fit / Disadvantage
- -- : Very poor fit / Strong disadvantage

## Alternatives Considered
One alternative considered was using a single database for all data, applying stricter access control and encryption. While this would simplify the architecture, it would expose all data to higher risks if the database is compromised. Another alternative was database sharding, where multiple shards would be used for scalability while keeping all data in the same database. However, this would not provide the same level of isolation and security for sensitive data as two separate databases.

## Conclusion
Given the need for heightened security, isolating sensitive data into a separate database is the best option. This approach allows for more controlled access, increased encryption, and a stronger overall security model while balancing the added complexity and performance considerations
