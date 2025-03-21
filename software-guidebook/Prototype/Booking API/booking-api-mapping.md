# Booking.com API

## Domain Model to API Mapping

### Attribute Mapping Table

| Domain Model Attribute | Input for API | Populated by API | Provided by User | Stored in App |
|------------------------|---------------|-----------------|------------------|--------------|
| Verblijf.startDatum | Booking /search (checkin_date) | | x | x |
| Verblijf.eindDatum | Booking /search (checkout_date) | | x | x |
| Verblijfplaats.locatie | Booking /search (dest_id, dest_type) | | x | x |
| Verblijfplaats.prijs | | Booking /search (hotel price data) | | x |
| Locatie.lat | | Booking /search (coordinates) | | x |
| Locatie.lon | | Booking /search (coordinates) | | x |

## Data Gaps Analysis

### Missing in Domain Model but Required by API
- Number of adults (`adults_number`)
- Number of rooms (`room_number`)
- Currency preference (`filter_by_currency`)
- Language/locale preference (`locale`)
- Sorting preference (`order_by`)
- Units of measurement (`units`)

### Proposed Solution for Missing Data
The following attributes should be added to the domain model to accommodate the Booking.com API requirements:

1. Add to `Trip` class:
   - `aantalVolwassenen: int` - Number of adults for the trip
   - `aantalKamers: int` - Number of rooms required
   - `valuta: String` - Preferred currency (e.g., "EUR")
   - `taal: String` - Preferred language (e.g., "nl-NL")

2. Add to `Verblijf` class:
   - `aantalKamers: int` - Number of rooms for this specific stay

### Missing in API Response but Needed for Domain Model
This section will be completed after analyzing actual API responses.

## API Requirements

### Required Parameters
- `checkin_date` - Check-in date (Format: YYYY-MM-DD)
- `checkout_date` - Check-out date (Format: YYYY-MM-DD)
- `dest_id` - Destination ID (numeric)
- `dest_type` - Destination type (e.g., "city")
- `adults_number` - Number of adults
- `room_number` - Number of rooms
- `filter_by_currency` - Currency code (e.g., "EUR")
- `locale` - Language code (e.g., "nl-NL")
- `order_by` - Sort order (e.g., "popularity")
- `units` - Unit system (e.g., "metric")

### Optional Parameters
- `children_number` - Number of children
- `children_ages` - Ages of children (comma-separated)
- `categories_filter_ids` - Filter IDs
- `include_adjacency` - Include nearby locations
- `page_number` - Page number for pagination 