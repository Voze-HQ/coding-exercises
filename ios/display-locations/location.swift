//
//  location.swift
//  display-locations
//
//  Created by Nathan Perry on 9/28/24.
//

import Foundation

enum LocationType: String, CaseIterable {
    case restaurant
    case museum
    case park
    case landmark
    case cafe
    case bar
    case unknown

    var label: String {
        return rawValue.capitalized
    }

    static func displayedCases() -> [LocationType] {
        allCases.filter { $0 != .unknown }.sorted(by: { type1, type2 in
            type1.rawValue < type2.rawValue
        })
    }
}

struct Location: Decodable, Identifiable {
    let id: Int
    let latitude: Double
    let longitude: Double
    let name: String
    let type: LocationType
    let description: String
    let estimatedRevenueMillions: Double

    enum CodingKeys: String, CodingKey {
        case id
        case latitude
        case longitude
        case name
        case type
        case description
        case estimatedRevenueMillions
        case attributes
    }
    
    init(id: Int, latitude: Double, longitude: Double, name: String, type: LocationType, description: String, estimatedRevenueMillions: Double) {
        self.id = id
        self.latitude = latitude
        self.longitude = longitude
        self.name = name
        self.type = type
        self.description = description
        self.estimatedRevenueMillions = estimatedRevenueMillions
    }

    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        id = try container.decode(Int.self, forKey: .id)
        latitude = try container.decode(Double.self, forKey: .latitude)
        longitude = try container.decode(Double.self, forKey: .longitude)
        let attributes = try container.decode([LocationAttribute].self, forKey: .attributes)
        let nameAttribute = attributes.first { $0.type == "name" }
        if case .string(let nameValue) = nameAttribute?.value {
            name = nameValue
        } else {
            name = ""
        }
        let typeAttribute = attributes.first { $0.type == "location_type" }
        if case .string(let typeValue) = typeAttribute?.value,
           let locationType = LocationType(rawValue: typeValue) {
            type = locationType
        } else {
            type = .unknown
        }
        let descriptionAttribute = attributes.first { $0.type == "description" }
        if case .string(let descriptionValue) = descriptionAttribute?.value {
            description = descriptionValue
        } else {
            description = ""
        }
        let revenueAttribute = attributes.first { $0.type == "estimated_revenue_millions" }
        if case .double(let revenueValue) = revenueAttribute?.value {
            estimatedRevenueMillions = revenueValue
        } else {
            estimatedRevenueMillions = 0
        }
    }
}
