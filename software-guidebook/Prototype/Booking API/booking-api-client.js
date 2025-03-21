/**
 * Simple JavaScript client for the Booking.com API
 * This demonstrates how to integrate the API with the Triptop domain model
 */

// For Node.js compatibility
const nodeFetch = typeof fetch !== 'undefined' ? fetch : require('node-fetch');

const API_KEY = '815ae4d42amshbace0e99000ee2bp18e38bjsnd54caac934e1';

class BookingApiClient {
  constructor(apiKey) {
    this.apiKey = apiKey;
    this.baseUrl = 'https://booking-com.p.rapidapi.com/v1/hotels';
    this.headers = {
      'X-RapidAPI-Key': this.apiKey,
      'X-RapidAPI-Host': 'booking-com.p.rapidapi.com'
    };
  }

  /**
   * Search for locations to get dest_id and dest_type
   * @param {string} locationName - Name of the location to search for
   * @param {string} locale - Language preference (e.g., 'nl')
   * @returns {Promise} - Promise with location search results
   */
  async searchLocations(locationName, locale = 'nl') {
    const url = `${this.baseUrl}/locations?name=${encodeURIComponent(locationName)}&locale=${locale}`;
    
    try {
      const response = await nodeFetch(url, {
        method: 'GET',
        headers: this.headers
      });
      
      return await response.json();
    } catch (error) {
      console.error('Error searching locations:', error);
      throw error;
    }
  }

  /**
   * Search for hotels based on Verblijf parameters
   * @param {Object} verblijf - Object representing a Verblijf from the domain model
   * @param {Object} trip - Object representing a Trip from the domain model (for additional parameters)
   * @returns {Promise} - Promise with hotel search results
   */
  async searchHotels(verblijf, trip) {
    // Extract parameters from domain model
    const checkinDate = verblijf.startDatum; // Format should be YYYY-MM-DD
    const checkoutDate = verblijf.eindDatum; // Format should be YYYY-MM-DD
    
    // These parameters would come from the extended domain model
    const adultsNumber = trip.aantalVolwassenen || 2;
    const roomNumber = trip.aantalKamers || 1;
    const currency = trip.valuta || 'EUR';
    const locale = trip.taal || 'nl';
    
    // Location info would come from a previous location search
    // In a real implementation, this would be stored with the Verblijfplaats
    const destId = verblijf.verblijfplaats.locationId || '1060'; // Example: Amsterdam
    const destType = verblijf.verblijfplaats.locationType || 'city';
    
    const url = `${this.baseUrl}/search?` + 
      `checkin_date=${checkinDate}&` +
      `checkout_date=${checkoutDate}&` +
      `dest_id=${destId}&` +
      `dest_type=${destType}&` +
      `adults_number=${adultsNumber}&` +
      `room_number=${roomNumber}&` +
      `filter_by_currency=${currency}&` +
      `locale=${locale}&` +
      `order_by=popularity&` +
      `units=metric`;
    
    try {
      const response = await nodeFetch(url, {
        method: 'GET',
        headers: this.headers
      });
      
      const data = await response.json();
      
      // Check if we have an error response
      if (data.detail) {
        console.error('API Error:', data.detail);
        return [];
      }
      
      // Map API response back to domain model format
      return this.mapResponseToDomainModel(data);
    } catch (error) {
      console.error('Error searching hotels:', error);
      throw error;
    }
  }
  
  /**
   * Get details for a specific hotel
   * @param {string} hotelId - ID of the hotel to get details for
   * @param {string} locale - Language preference
   * @returns {Promise} - Promise with hotel details
   */
  async getHotelDetails(hotelId, locale = 'nl') {
    const url = `${this.baseUrl}/data?hotel_id=${hotelId}&locale=${locale}`;
    
    try {
      const response = await nodeFetch(url, {
        method: 'GET',
        headers: this.headers
      });
      
      return await response.json();
    } catch (error) {
      console.error('Error getting hotel details:', error);
      throw error;
    }
  }
  
  /**
   * Maps the API response to domain model objects
   * @param {Object} apiResponse - Response from the API
   * @returns {Array} - Array of Verblijfplaats objects
   */
  mapResponseToDomainModel(apiResponse) {
    // Extract the results array from the response
    const hotels = apiResponse.results || [];
    
    // Map each hotel to a Verblijfplaats object
    return hotels.map(hotel => {
      return {
        // Map to Verblijfplaats properties
        locatie: {
          lat: hotel.latitude,
          lon: hotel.longitude,
          // Additional information not in domain model but useful
          naam: hotel.hotel_name,
          adres: hotel.address,
          stad: hotel.city,
          land: hotel.country
        },
        prijs: hotel.price_breakdown ? hotel.price_breakdown.gross_price : null,
        
        // Additional information from the API that might be useful
        hotelId: hotel.hotel_id,
        sterrenClassificatie: hotel.class,
        reviewScore: hotel.review_score,
        beschikbareKamers: hotel.rooms_available || 0,
        fotoUrl: hotel.main_photo_url
      };
    });
  }
}

// Example usage:
async function searchHotelsExample() {
  const client = new BookingApiClient(API_KEY);
  
  // Example domain model objects
  const trip = {
    aantalVolwassenen: 2,
    aantalKamers: 1,
    valuta: 'EUR',
    taal: 'nl-NL'
  };
  
  const verblijf = {
    startDatum: '2024-07-15',
    eindDatum: '2024-07-20',
    verblijfplaats: {
      locationId: '1060', // Amsterdam
      locationType: 'city'
    }
  };
  
  try {
    // First, search for a location
    const locations = await client.searchLocations('Amsterdam');
    console.log('Location search results:', locations);
    
    // Then, search for hotels
    const hotels = await client.searchHotels(verblijf, trip);
    console.log('Hotel search results mapped to domain model:', hotels);
    
    // Get details for a specific hotel (assuming the first result from search)
    if (hotels.length > 0) {
      const hotelDetails = await client.getHotelDetails(hotels[0].hotelId);
      console.log('Hotel details:', hotelDetails);
    }
  } catch (error) {
    console.error('Error in example:', error);
  }
}

// Export the class for Node.js compatibility
if (typeof module !== 'undefined' && module.exports) {
  module.exports = { BookingApiClient };
  // Make the class available globally for our test script
  global.BookingApiClient = BookingApiClient;
}

// Uncomment to run the example:
// searchHotelsExample(); 