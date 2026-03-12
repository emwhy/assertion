package org.emw.assertflow.regression;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emw.assertflow.exception.AssertionGroupError;
import org.emw.assertflow.json.JsonAssertor;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class JsonTest implements JsonAssertor {
    @Test
    public void testJson() {
        final String testJson = """
                {
                  "university_system": {
                    "name": "Global Tech Institute",
                    "founded_year": 1985,
                    "end_year": null,
                    "open": true,
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
        assertJson(testJson).expect(json -> {
            json.node("/university_system/name").to.caseInsensitively.be("global Tech Institute");
            json.node("/university_system/name").to.be("Global Tech Institute");
            json.node("/university_system/founded_year").to.be(1985);
            json.node("/university_system/founded_year").to.not.be(198);
            json.node("/university_system/global_stats/international_ratio").to.be(0.22);
            json.node("/university_system/founded_year").to.not.be.stringType();
            json.node("/university_system/end_year").to.be.nullValue();
            json.node("/university_system").to.not.be.stringType();
            json.node("/university_system/name", nameNode -> {
                nameNode.to.not.be.string.empty();
                nameNode.to.be.string.startWith("Global");
                nameNode.to.be.string.endWith("Institute");
                nameNode.to.be.string.contain("Tech");
                nameNode.to.caseInsensitively.be.string.startWith("GLOBAL");
                nameNode.to.caseInsensitively.be.string.endWith("INSTITUTE");
                nameNode.to.caseInsensitively.be.string.contain("TECH");
            });
            json.node("/university_system/founded_year").to.not.be.stringType();
            json.node("/university_system/founded_year").to.be.numberType();
            json.node("/university_system/founded_year").to.be.number.greaterThan(1970);
            json.node("/university_system/founded_year").to.be.number.lessThan(2000);
            json.node("/university_system/founded_year").to.be.number.between(1000, 2000);
            json.node("/university_system/founded_year").to.not.be.booleanType();
            json.node("/university_system/open").to.be.booleanType();
            json.node("/university_system/open").to.be.bool.trueValue();
            json.node("/university_system/global_stats/international_ratio").to.be(0.22);
            json.node("/university_system").to.excluding("/campuses", "/global_stats/total_students", "end_year").be("""
                {
                    "name": "Global Tech Institute",
                    "founded_year": 1985,
                    "open": true,
                    "global_stats": {
                      "total_students": 154000000,
                      "international_ratio": 0.22,
                      "affiliations": ["Global Edu Group", "Research Alliance"]
                    }
                }
                """);
            json.to.findJson("""
                          {
                            "access": "24/7",
                            "type": "Library",
                            "floors": [
                              { "level": 1, "section": "Reference" },
                              { "level": 2, "section": "Quiet Study" }
                            ]
                          }
                    """);
            json.to.excluding("/access").findJson("""
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
    }

    @Test
    public void testJsonArray() {
        final String testJson = """
                  [
                    {
                      "id": "ST-001",
                      "name": "Alice Smith",
                      "rooms": ["room1-1", "room1-2", "room1-3"],
                      "numbers": [0.4, 4, 8],
                      "grades": [
                        { "exam": "Midterm", "score": 88 },
                        { "exam": "Final", "score": 92 }
                      ]
                    },
                    {
                      "id": "ST-002",
                      "name": "Bob Johnson",
                      "rooms": ["room2-1", "room2-2", "room2-3"],
                      "numbers": [4, 48, .8],
                      "grades": [
                        { "exam": "Midterm", "score": 75 },
                        { "exam": "Final", "score": 81 }
                      ]
                    }
                  ]
                """;
        assertJsonArray(testJson).expect(jsonNodes -> {
            jsonNodes.first().node("/id").to.be.string.startWith("ST-001");
            jsonNodes.last().node("/id").to.be.string.startWith("ST-002");
            jsonNodes.last().nodes("/grades").first().node("/score").to.be.number.lessThan(88);
            jsonNodes.to.be(testJson);
            jsonNodes.to.not.be("['test']");
            jsonNodes.to.findJson("""
                [
                    { "exam": "Midterm", "score": 75 },
                    { "exam": "Final", "score": 81 }
                ]
                """);
            jsonNodes.to.not.findJson("""
                [
                    { "exam": "Midterm", "score": 750 },
                    { "exam": "Final", "score": 81 }
                ]
                """);
            jsonNodes.to.caseInsensitively.findJson("""
                [
                    { "exam": "midterm", "score": 75 },
                    { "exam": "final", "score": 81 }
                ]
                """);
            jsonNodes.to.excluding("/rooms", "/numbers").allMatch("""
                    {
                      "id": "ST-001",
                      "name": "Alice Smith",
                      "grades": [
                        { "exam": "Midterm", "score": 88 },
                        { "exam": "Final", "score": 92 }
                      ]
                    }
                    """, """
                    {
                      "id": "ST-002",
                      "name": "Bob Johnson",
                      "grades": [
                        { "exam": "Midterm", "score": 75 },
                        { "exam": "Final", "score": 81 }
                      ]
                    }

                    """);
            jsonNodes.first().nodes("/rooms").to.allMatch("room1-1", "room1-2", "room1-3");
            jsonNodes.first().nodes("/rooms").to.caseInsensitively.allMatch("ROOM1-1", "ROOM1-2", "ROOM1-3");
            jsonNodes.first().nodes("/rooms").to.inAnyOrder.allMatch("room1-2", "room1-3", "room1-1");
            jsonNodes.first().nodes("/numbers").to.allMatch(.4, 4, 8);
            jsonNodes.to.inAnyOrder.excluding("/rooms", "/numbers").allMatch("""
                    {
                      "id": "ST-002",
                      "name": "Bob Johnson",
                      "grades": [
                        { "exam": "Midterm", "score": 75 },
                        { "exam": "Final", "score": 81 }
                      ]
                    }
                    """, """
                    {
                      "id": "ST-001",
                      "name": "Alice Smith",
                      "grades": [
                        { "exam": "Midterm", "score": 88 },
                        { "exam": "Final", "score": 92 }
                      ]
                    }
                    """);
            jsonNodes.to.excluding("/rooms", "/numbers").not.allMatch("""
                    {
                      "id": "ST-002",
                      "name": "Bob Johnson",
                      "grades": [
                        { "exam": "Midterm", "score": 75 },
                        { "exam": "Final", "score": 81 }
                      ]
                    }
                    """, """
                    {
                      "id": "ST-001",
                      "name": "Alice Smith",
                      "grades": [
                        { "exam": "Midterm", "score": 88 },
                        { "exam": "Final", "score": 92 }
                      ]
                    }
                    """);
            jsonNodes.to.excluding("/rooms", "/numbers").contain("""
                    {
                      "id": "ST-002",
                      "name": "Bob Johnson",
                      "grades": [
                        { "exam": "Midterm", "score": 75 },
                        { "exam": "Final", "score": 81 }
                      ]
                    }
                    """);
            jsonNodes.to.excluding("/rooms", "/numbers").contain("""
                    {
                      "id": "ST-002",
                      "name": "Bob Johnson",
                      "grades": [
                        { "exam": "Midterm", "score": 75 },
                        { "exam": "Final", "score": 81 }
                      ]
                    }
                    """, """
                    {
                      "id": "ST-001",
                      "name": "Alice Smith",
                      "grades": [
                        { "exam": "Midterm", "score": 88 },
                        { "exam": "Final", "score": 92 }
                      ]
                    }
                    """);
            jsonNodes.to.excluding("/rooms", "/numbers").not.contain("""
                    {
                      "id": "ST-002",
                      "name": "Bob Johnson Test",
                      "grades": [
                        { "exam": "Midterm", "score": 75 },
                        { "exam": "Final", "score": 81 }
                      ]
                    }
                    """, """
                    {
                      "id": "ST-001",
                      "name": "Alice Smith",
                      "grades": [
                        { "exam": "Midterm", "score": 88 },
                        { "exam": "Final", "score": 92 }
                      ]
                    }
                    """);
            jsonNodes.first().nodes("/rooms").to.contain("room1-2", "room1-3");
            jsonNodes.to.be.sizeOf(2);
            jsonNodes.to.not.be.empty();
        });
        assertJsonArray("[]").expect(jsonNodes -> jsonNodes.to.be.empty());
    }

    @Test
    public void testJsonDate() {
        final String testJson = """
                {
                  "date_formats": [
                    {
                      "formatter": "DateTimeFormatter.BASIC_ISO_DATE",
                      "pattern": "yyyyMMdd",
                      "example": "20260309"
                    },
                    {
                      "formatter": "DateTimeFormatter.ISO_DATE",
                      "pattern": "yyyy-MM-dd+HH:mm",
                      "example": "2026-03-09-04:00"
                    },
                    {
                      "formatter": "DateTimeFormatter.ISO_OFFSET_DATE",
                      "pattern": "yyyy-MM-dd+HH:mm",
                      "example": "2026-03-09-04:00"
                    },
                    {
                      "formatter": "DateTimeFormatter.ISO_LOCAL_DATE",
                      "pattern": "yyyy-MM-dd",
                      "example": "2026-03-09"
                    },
                    {
                      "formatter": "DateTimeFormatter.ISO_ORDINAL_DATE",
                      "pattern": "yyyy-DDD",
                      "example": "2026-068"
                    },
                    {
                      "formatter": "DateTimeFormatter.ISO_WEEK_DATE",
                      "pattern": "YYYY-w-e",
                      "example": "2026-W11-1"
                    },
                    {
                      "formatter": "DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)",
                      "pattern": "EEEE, MMMM d, yyyy",
                      "example": "Monday, March 9, 2026"
                    },
                    {
                      "formatter": "DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)",
                      "pattern": "MMMM d, yyyy",
                      "example": "March 9, 2026"
                    },
                    {
                      "formatter": "DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)",
                      "pattern": "MMM d, yyyy",
                      "example": "Mar 9, 2026"
                    },
                    {
                      "formatter": "DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)",
                      "pattern": "M/d/yy",
                      "example": "3/9/26"
                    },
                    {
                      "formatter": "DateTimeFormatter.ofPattern(\\"MM/dd/yyyy\\")",
                      "pattern": "MM/dd/yyyy",
                      "example": "03/09/2026"
                    },
                    {
                      "formatter": "DateTimeFormatter.ofPattern(\\"M/d/yyyy\\")",
                      "pattern": "M/d/yyyy",
                      "example": "3/9/2026"
                    }
                  ]
                }
                
                """;
        assertJson(testJson).expect(json -> {
            json.nodes("/date_formats").forEach(node -> {
                node.node("/example").to.be.dateType();
                node.node("/example").to.be.date.of(LocalDate.of(2026, 3, 9));
            });
        });
    }

    @Test
    public void testJsonDateTime() {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        final LocalDateTime hoursPastDateTime = now.minusHours(5);
        final LocalDateTime daysPastDateTime = now.minusDays(15);
        final LocalDateTime hoursFutureDateTime = now.plusHours(5);
        final LocalDateTime daysFutureDateTime = now.plusDays(15);
        final String testJson = """
                {
                  "datetime_formats": [
                    {
                      "formatter": "DateTimeFormatter.ISO_LOCAL_DATE_TIME",
                      "pattern": "yyyy-MM-dd'T'HH:mm:ss",
                      "example": "2026-03-09T13:24:45"
                    },
                    {
                      "formatter": "DateTimeFormatter.ISO_OFFSET_DATE_TIME",
                      "pattern": "yyyy-MM-dd'T'HH:mm:ssXXX",
                      "example": "2026-03-09T13:24:45-04:00"
                    },
                    {
                      "formatter": "DateTimeFormatter.ISO_ZONED_DATE_TIME",
                      "pattern": "yyyy-MM-dd'T'HH:mm:ssXXX'['VV']'",
                      "example": "2026-03-09T13:24:45-04:00[America/New_York]"
                    },
                    {
                      "formatter": "DateTimeFormatter.ISO_DATE_TIME",
                      "pattern": "ISO-8601 with optional offset/zone",
                      "example": "2026-03-09T13:24:45.123-04:00"
                    },
                    {
                      "formatter": "DateTimeFormatter.RFC_1123_DATE_TIME",
                      "pattern": "EEE, d MMM yyyy HH:mm:ss z",
                      "example": "Mon, 9 Mar 2026 13:24:45 GMT"
                    },
                    {
                      "formatter": "DateTimeFormatter.ofPattern(\\"MM/dd/yyyy HH:mm:ss\\")",
                      "pattern": "MM/dd/yyyy HH:mm:ss",
                      "example": "03/09/2026 13:24:45"
                    },
                    {
                      "formatter": "DateTimeFormatter.ofPattern(\\"M/d/yyyy H:mm:ss\\")",
                      "pattern": "M/d/yyyy H:mm:ss",
                      "example": "3/9/2026 13:24:45"
                    },
                    {
                      "formatter": "DateTimeFormatter.ofPattern(\\"yyyy-MM-dd HH:mm:ss\\")",
                      "pattern": "yyyy-MM-dd HH:mm:ss",
                      "example": "2026-03-09 13:24:45"
                    }
                  ],
                  "relative_datetime": {
                    "hours_past": {
                      "example": "%s"
                    },
                    "days_past": {
                      "example": "%s"
                    },
                    "hours_future": {
                      "example": "%s"
                    },
                    "days_future": {
                      "example": "%s"
                    }
                  }
                }
                """.formatted(hoursPastDateTime.format(dateTimeFormatter), daysPastDateTime.format(dateTimeFormatter), hoursFutureDateTime.format(dateTimeFormatter), daysFutureDateTime.format(dateTimeFormatter));

        assertJson(testJson).expect(json -> {
            json.nodes("/datetime_formats").stream().forEach(dateFormatNode -> {
                dateFormatNode.node("/example", exampleNode -> {
                    exampleNode.to.be.dateTimeType();
                    exampleNode.to.be.dateTime.sameOrAfter(LocalDateTime.of(2026, 3, 9, 13, 24, 45));
                    exampleNode.to.be.dateTime.before(LocalDateTime.of(2026, 3, 9, 13, 25));
                    exampleNode.to.be.dateTime.after(LocalDateTime.of(2026, 3, 8, 13, 25));
                    exampleNode.to.be.dateTime.sameDateAs(LocalDate.of(2026, 3, 9));
                });
            });
            json.node("/relative_datetime", relativeDateTimeNode -> {
                relativeDateTimeNode.node("/hours_past/example", exampleNode -> {
                    exampleNode.to.be.dateTimeType();
                    exampleNode.to.be.stringType();
                    exampleNode.to.be.dateTime.sameDateAs(hoursPastDateTime.toLocalDate());
                    exampleNode.to.be.dateTime.of(hoursPastDateTime);
                    exampleNode.to.be.dateTime.withinPastHours(6);
                    exampleNode.to.not.be.dateTime.withinPastHours(4);
                    exampleNode.to.be.dateTime.moreThanHoursInPast(4);
                    exampleNode.to.not.be.dateTime.moreThanDaysInPast(1);
                    exampleNode.to.not.be.dateTime.withinPastHours(4);
                    exampleNode.to.be.dateTime.withinPastDays(1);
                    exampleNode.to.not.be.dateTime.withinHours(4);
                    exampleNode.to.not.be.dateTime.moreThanHoursInFuture(4);
                    exampleNode.to.not.be.dateTime.moreThanDaysInFuture(1);
                });
                relativeDateTimeNode.node("/days_past/example", exampleNode -> {
                    exampleNode.to.be.dateTimeType();
                    exampleNode.to.be.stringType();
                    exampleNode.to.be.dateTime.sameDateAs(daysPastDateTime.toLocalDate());
                    exampleNode.to.be.dateTime.of(daysPastDateTime);
                    exampleNode.to.not.be.dateTime.withinPastHours(6);
                    exampleNode.to.be.dateTime.withinPastDays(16);
                    exampleNode.to.not.be.dateTime.withinPastDays(14);
                    exampleNode.to.be.dateTime.moreThanHoursInPast(4);
                    exampleNode.to.be.dateTime.moreThanDaysInPast(14);
                    exampleNode.to.not.be.dateTime.withinPastHours(4);
                    exampleNode.to.not.be.dateTime.withinHours(4);
                    exampleNode.to.not.be.dateTime.moreThanHoursInFuture(4);
                    exampleNode.to.not.be.dateTime.moreThanDaysInFuture(1);
                });
                relativeDateTimeNode.node("/hours_future/example", exampleNode -> {
                    exampleNode.to.be.dateTimeType();
                    exampleNode.to.be.stringType();
                    exampleNode.to.be.dateTime.sameDateAs(hoursFutureDateTime.toLocalDate());
                    exampleNode.to.be.dateTime.of(hoursFutureDateTime);
                    exampleNode.to.not.be.dateTime.withinPastHours(6);
                    exampleNode.to.be.dateTime.withinPastDays(16);
                    exampleNode.to.be.dateTime.withinPastDays(14);
                    exampleNode.to.not.be.dateTime.moreThanHoursInPast(4);
                    exampleNode.to.not.be.dateTime.moreThanDaysInPast(14);
                    exampleNode.to.not.be.dateTime.withinPastHours(4);
                    exampleNode.to.not.be.dateTime.withinHours(4);
                    exampleNode.to.be.dateTime.withinHours(6);
                    exampleNode.to.be.dateTime.moreThanHoursInFuture(4);
                    exampleNode.to.not.be.dateTime.moreThanHoursInFuture(6);
                    exampleNode.to.not.be.dateTime.moreThanDaysInFuture(1);
                });
                relativeDateTimeNode.node("/days_future/example", exampleNode -> {
                    exampleNode.to.be.dateTimeType();
                    exampleNode.to.be.stringType();
                    exampleNode.to.be.dateTime.sameDateAs(daysFutureDateTime.toLocalDate());
                    exampleNode.to.be.dateTime.of(daysFutureDateTime);
                    exampleNode.to.not.be.dateTime.withinPastHours(6);
                    exampleNode.to.not.be.dateTime.withinPastDays(16);
                    exampleNode.to.not.be.dateTime.withinPastDays(14);
                    exampleNode.to.not.be.dateTime.moreThanHoursInPast(4);
                    exampleNode.to.not.be.dateTime.moreThanDaysInPast(14);
                    exampleNode.to.not.be.dateTime.withinPastHours(4);
                    exampleNode.to.not.be.dateTime.withinHours(4);
                    exampleNode.to.be.dateTime.moreThanHoursInFuture(4);
                    exampleNode.to.not.be.dateTime.moreThanDaysInFuture(16);
                    exampleNode.to.be.dateTime.moreThanDaysInFuture(14);
                });
            });
        });

    }

    @Test
    public void testJsonTime() {
        final LocalTime relativeTime = LocalTime.now().minusMinutes(30);
        final String relativeTimeString = relativeTime.format(DateTimeFormatter.ofPattern("H:mm"));
        final String testJson = """
                {
                  "time_formats": [
                    {
                      "formatter": "DateTimeFormatter.ISO_LOCAL_TIME",
                      "pattern": "HH:mm:ss",
                      "example": "13:25:30"
                    },
                    {
                      "formatter": "DateTimeFormatter.ISO_TIME",
                      "pattern": "HH:mm:ssXXX",
                      "example": "13:25:30-04:00"
                    },
                    {
                      "formatter": "DateTimeFormatter.ISO_OFFSET_TIME",
                      "pattern": "HH:mm:ssXXX",
                      "example": "13:25:30-04:00"
                    },
                    {
                      "formatter": "DateTimeFormatter.ofPattern(\\"HH:mm:ss\\")",
                      "pattern": "HH:mm:ss",
                      "example": "13:25:30"
                    },
                    {
                      "formatter": "DateTimeFormatter.ofPattern(\\"HH:mm\\")",
                      "pattern": "HH:mm",
                      "example": "13:25"
                    },
                    {
                      "formatter": "DateTimeFormatter.ofPattern(\\"h:mm a\\")",
                      "pattern": "h:mm a",
                      "example": "1:25 PM"
                    },
                    {
                      "formatter": "DateTimeFormatter.ofPattern(\\"h:mm:ss a\\")",
                      "pattern": "h:mm:ss a",
                      "example": "1:25:30 PM"
                    },
                    {
                      "formatter": "DateTimeFormatter.ofPattern(\\"H:mm:ss\\")",
                      "pattern": "H:mm:ss",
                      "example": "13:25:30"
                    },
                    {
                      "formatter": "DateTimeFormatter.ofPattern(\\"H:mm\\")",
                      "pattern": "H:mm",
                      "example": "13:25"
                    }
                  ],
                  relative_time:
                    {
                      "formatter": "DateTimeFormatter.ofPattern(\\"H:mm\\")",
                      "pattern": "H:mm",
                      "example": "%s"
                    }
                }
                """.formatted(relativeTimeString);
        assertJson(testJson).expect(json -> {
            json.nodes("/time_formats").forEach(timeFormatNode -> {
                timeFormatNode.node("/example").to.be.stringType();
                timeFormatNode.node("/example").to.be.timeType();
                timeFormatNode.node("/example").to.be.time.sameOrAfter(LocalTime.of(13, 25));
                timeFormatNode.node("/example").to.be.time.before(LocalTime.of(13, 26));
            });
            json.node("/relative_time/example", exampleNode -> {
                exampleNode.to.be.stringType();
                exampleNode.to.be.timeType();
                exampleNode.to.be.time.before(LocalTime.now());
                exampleNode.to.be.time.withinPastHours(1);
                exampleNode.to.not.be.time.withinHours(1);
                exampleNode.to.not.be.time.moreThanHoursInFuture(4);
                exampleNode.to.not.be.time.moreThanHoursInPast(1);
            });
        });

        expectError(() -> {
            assertJson(testJson).expect(json -> {
                json.nodes("/time_formats").forEach(timeFormatNode -> {
                    timeFormatNode.node("/example").to.be.numberType();
                    timeFormatNode.node("/example").to.not.be.stringType();
                    timeFormatNode.node("/example").to.be.time.before(LocalTime.of(13, 25));
                    timeFormatNode.node("/example").to.be.time.after(LocalTime.of(13, 26));
                });
            });
        }, "Expected node to be a type of number.",
                "Expected node to not be a type of string.",
                "Expected actual value('13:25:30.000') of 'JsonNode Time' to be before ",
                "Expected actual value('13:25:30.000') of 'JsonNode Time' to be after "
                );

        expectError(() -> {
            assertJson(testJson).expect(json -> {
                json.node("/relative_time/example", exampleNode -> {
                    exampleNode.to.be.time.after(LocalTime.now());
                    exampleNode.to.be.time.withinHours(1);
                    exampleNode.to.be.time.moreThanHoursInFuture(4);
                    exampleNode.to.be.time.moreThanHoursInPast(1);
                });
            });
        }, "Expected actual value('%s:00.000') of 'JsonNode Time' to be after ".formatted(relativeTimeString),
                "Expected actual value('%s:00.000') of 'JsonNode Time' to be within 1 hours from ".formatted(relativeTimeString),
                "Expected actual value('%s:00.000') of 'JsonNode Time' to be more than 4 hours in future from ".formatted(relativeTimeString),
                "Expected actual value('%s:00.000') of 'JsonNode Time' to be more than 1 hours in past from ".formatted(relativeTimeString));
    }


    private void expectError(@NonNull AssertionErrorAction action, @NonNull String... errorMessages) {
        try {
            action.expectAssertionError();
        } catch (AssertionGroupError ex) {
            if (errorMessages.length == 0) {
                System.out.println("Error message: \n" + ex.getMessage());
            } else {
                for (String errorMessage : errorMessages) {
                    if (ex.getMessage() == null || ex.getMessages().stream().noneMatch(s -> s.contains(errorMessage))) {
                        throw new AssertionError("Unexpected AssertionError message. Expected: [" + errorMessage + "]\nActual: \n" + ex.getMessage());
                    }
                }
            }
            return;
        }
        throw new AssertionError("Expected AssertionError.");
    }

    interface AssertionErrorAction {
        void expectAssertionError();
    }
}
