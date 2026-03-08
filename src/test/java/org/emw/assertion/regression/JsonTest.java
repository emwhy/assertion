package org.emw.assertion.regression;

import org.emw.assertion.json.JsonAssertor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.*;

public class JsonTest implements JsonAssertor {
    @Test
    public void testJsonSyntax() {
        assertJson("").expect(json -> {

        });
    }

    @Test
    public void testJson() {
        final String testJson = """
                {
                  "university_system": {
                    "name": "Global Tech Institute",
                    "founded_year": 1985,
                    "campuses": [
                      {
                        "location": "North Campus",
                        "departments": [
                          {
                            "name": "Computer Science",
                            "head": {
                              "name": "Dr. Sarah Chen",
                              "contact": {
                                "email": "s.chen@gti.edu",
                                "office": "Bldg A, 402"
                              }
                            },
                            "courses": [
                              {
                                "code": "CS101",
                                "title": "Intro to Algorithms",
                                "enrollment": {
                                  "total": 120,
                                  "students": [
                                    {
                                      "id": "ST-001",
                                      "name": "Alice Smith",
                                      "grades": [
                                        { "exam": "Midterm", "score": 88 },
                                        { "exam": "Final", "score": 92 }
                                      ]
                                    },
                                    {
                                      "id": "ST-002",
                                      "name": "Bob Johnson",
                                      "grades": [
                                        { "exam": "Midterm", "score": 75 },
                                        { "exam": "Final", "score": 81 }
                                      ]
                                    }
                                  ]
                                }
                              }
                            ],
                            "research_labs": [
                              {
                                "lab_name": "AI Foundations",
                                "projects": [
                                  {
                                    "title": "Neural Mapping",
                                    "funding": {
                                      "source": "Tech Corp",
                                      "amount": 500000,
                                      "currency": "USD"
                                    }
                                  }
                                ]
                              }
                            ]
                          }
                        ]
                      },
                      {
                        "location": "South Campus",
                        "specialization": "Medical Sciences",
                        "facilities": [
                          {
                            "type": "Hospital",
                            "capacity": 250,
                            "wards": ["ICU", "Pediatrics", "Cardiology"]
                          },
                          {
                            "type": "Library",
                            "access": "24/7",
                            "floors": [
                              { "level": 1, "section": "Reference" },
                              { "level": 2, "section": "Quiet Study" }
                            ]
                          }
                        ]
                      }
                    ],
                    "global_stats": {
                      "total_students": 15400,
                      "international_ratio": 0.22,
                      "affiliations": ["Global Edu Group", "Research Alliance"]
                    }
                  }
                }
                
                """;
        final JSONObject jo = new JSONObject(testJson);

        assertJson(testJson).expect(json -> {
            json.node("/university_system/name").to.caseInsensitively.be("global Tech Institute");
            json.node("/university_system/name").to.be("Global Tech Institute");
            json.node("/university_system/founded_year").to.be(1985);
            json.node("/university_system/founded_year").to.not.be(198);
            json.node("/university_system/global_stats/international_ratio").to.be(0.22);
            json.node("/university_system/founded_year").to.exists();
            json.node("/university_system/name").to.not.be.string().empty();
            json.node("/university_system").to.excluding("/campuses").excluding("/global_stats/total_students").be("""
                {
                    "name": "Global Tech Institute",
                    "founded_year": 1985,
                    "global_stats": {
                      "total_students": 154000000,
                      "international_ratio": 0.22,
                      "affiliations": ["Global Edu Group", "Research Alliance"]
                    }
                }
                """);
            json.to.containJson("""
                          {
                            "access": "24/7",
                            "type": "Library",
                            "floors": [
                              { "level": 1, "section": "Reference" },
                              { "level": 2, "section": "Quiet Study" }
                            ]
                          }
                    """);
            json.to.excluding("/access").containJson("""
                          {
                            "access": "24/7999",
                            "type": "Library",
                            "floors": [
                              { "level": 1, "section": "Reference" },
                              { "level": 2, "section": "Quiet Study" }
                            ]
                          }
                    """);
        });

//        System.out.println(getJsonObjectPointer("", jo));
//        generatePointers(jo, "");
        for (Map.Entry<String, Object> entry : getPointerValueMap(testJson).entrySet()) {
            System.out.println((entry.getKey() + ": " + entry.getValue()));
        }
    }

    public static Map<String, Object> getPointerValueMap(String jsonText) {
        final Map<String, Object> results = new TreeMap<>();
        final String trimmed = jsonText.trim();

        if (!trimmed.isEmpty()) {
            final Object root = trimmed.startsWith("[") ? new JSONArray(trimmed) : new JSONObject(trimmed);

            collectPointers(root, "", results);
        }
        return results;
    }

    private static void collectPointers(Object node, String pointer, Map<String, Object> results) {
        final String currentPath = pointer.isEmpty() ? "/" : pointer;

        // Store the current node in the map
        results.put(currentPath, node);

        if (node instanceof JSONObject obj) {
            for (Iterator<String> keys = obj.keys(); keys.hasNext(); ) {
                final String key = keys.next();
                // RFC 6901 Escaping
                final String escapedKey = key.replace("~", "~0").replace("/", "~1");
                collectPointers(obj.get(key), pointer + "/" + escapedKey, results);
            }
        } else if (node instanceof JSONArray array) {
            for (int i = 0; i < array.length(); i++) {
                collectPointers(array.get(i), pointer + "/" + i, results);
            }
        }
    }

    public static List<String> getAllPointers(String jsonText) {
        List<String> results = new ArrayList<>();
        String trimmed = jsonText.trim();

        if (trimmed.isEmpty()) return results;

        Object root = trimmed.startsWith("[") ? new JSONArray(trimmed) : new JSONObject(trimmed);
        collectPointers(root, "", results);

        return results;
    }

    private static void collectPointers(Object node, String pointer, List<String> results) {
        // Add the current path to the list
        results.add(pointer.isEmpty() ? "/" : pointer);

        if (node instanceof JSONObject obj) {
            for (Iterator<String> keys = obj.keys(); keys.hasNext(); ) {
                String key = keys.next();
                String escapedKey = key.replace("~", "~0").replace("/", "~1");
                collectPointers(obj.get(key), pointer + "/" + escapedKey, results);
            }
        } else if (node instanceof JSONArray array) {
            for (int i = 0; i < array.length(); i++) {
                collectPointers(array.get(i), pointer + "/" + i, results);
            }
        }
    }

    private static void generatePointers(Object node, String pointer) {
        // Print current pointer (root is represented as /)
        System.out.println(pointer.isEmpty() ? "/" : pointer);

        if (node instanceof JSONObject obj) {
            for (Iterator<String> keys = obj.keys(); keys.hasNext(); ) {
                String key = keys.next();
                // RFC 6901 Escaping: ~ -> ~0, / -> ~1
                String escapedKey = key.replace("~", "~0").replace("/", "~1");
                generatePointers(obj.get(key), pointer + "/" + escapedKey);
            }
        } else if (node instanceof JSONArray array) {
            for (int i = 0; i < array.length(); i++) {
                generatePointers(array.get(i), pointer + "/" + i);
            }
        }
    }

    private String getJsonObjectPointer(String pointerText, JSONObject jo) {
        for (String key : jo.keySet()) {
            Object data = jo.get(key);

            if (data instanceof JSONObject) {
                pointerText += getJsonObjectPointer(pointerText + "/" + key, (JSONObject) data);
            } else if (data instanceof JSONArray) {
                pointerText += getJsonArrayPointer(pointerText + "/" + key, (JSONArray) data);
            } else {
                pointerText += "/" + key + "/" + data.toString() + "\n";
            }
        }
        return pointerText;
    }

    private String getJsonArrayPointer(String pointerText, JSONArray ja) {
        for (int i = 0; i < ja.length(); i++) {
            Object data = ja.get(i);

            if (data instanceof JSONObject) {
                pointerText += getJsonObjectPointer(pointerText + "/" + i, (JSONObject) data);
            } else if (data instanceof JSONArray) {
                pointerText += getJsonArrayPointer(pointerText + "/" + i, (JSONArray) data);
            } else {
                pointerText += "/" + data.toString() + "\n";
            }
        }
        return pointerText;
    }

    private void parseJSON(HashMap<String, Object> map) {

    }


    @Test
    public void testJsonList() {
        final String testJson = """
                [
                  {
                    "category": "Consumer Electronics",
                    "region": "North America",
                    "inventory": [
                      {
                        "product_id": "SKU-9921",
                        "name": "Quantum Tablet Pro",
                        "specs": {
                          "display": {
                            "size": "12.9 inch",
                            "technology": "OLED",
                            "resolution": "2732 x 2048"
                          },
                          "sensors": ["LiDAR", "FaceID", "Ambient Light", "Gyroscope"]
                        },
                        "warehouses": [
                          {
                            "id": "WH-TX-01",
                            "stock_levels": {
                              "on_hand": 1240,
                              "reserved": 150,
                              "backordered": 0
                            },
                            "audit_history": [
                              { "date": "2024-01-15", "action": "restock", "quantity": 500 },
                              { "date": "2024-02-10", "action": "damage_writeoff", "quantity": 12 }
                            ]
                          },
                          {
                            "id": "WH-CA-05",
                            "stock_levels": {
                              "on_hand": 890,
                              "reserved": 45,
                              "backordered": 12
                            }
                          }
                        ]
                      },
                      {
                        "product_id": "SKU-4410",
                        "name": "Sonic Buds Gen 3",
                        "features": ["Active Noise Cancellation", "Spatial Audio", "IPX4 Rated"],
                        "pricing_tiers": [
                          { "type": "Retail", "price": 199.99, "currency": "USD" },
                          { "type": "Wholesale", "price": 145.00, "min_units": 50 }
                        ]
                      }
                    ]
                  },
                  {
                    "category": "Home Automation",
                    "region": "Europe",
                    "inventory": [
                      {
                        "product_id": "SKU-1102",
                        "name": "Smart Hub X",
                        "connectivity": ["Zigbee 3.0", "Matter", "Thread", "Wi-Fi 6"],
                        "support_contacts": [
                          {
                            "tier": "Level 1",
                            "methods": ["Email", "Live Chat"],
                            "availability": "24/7"
                          }
                        ]
                      }
                    ]
                  }
                ]
                
                
                """;
        final JSONObject jo = new JSONObject(testJson);

        System.out.println(jo);
    }
}
