package de.perdian.apps.calendarbuddy.modules.ai;

import java.util.List;

public class AiAnalyzerConstants {

    public static final String DEFAULT_MODEL = "z-ai/glm-5.3-flash";

    public static final List<String> AVAILABLE_MODELS = List.of(
        "anthropic/claude-sonnet-5.5",
        "z-ai/glm-5.3-flash",
        "deepseek/deepseek-v4.1-flash-1017"

    );

    public static final String DEFAULT_PDF_ENGINE = "pdf-text";

    public static final List<String> AVAILABLE_PDF_ENGINES = List.of(
        "pdf-text",
        "mistral-ocr",
        "native"
    );

    public static final String DATA_EXTRACTION_USER_PROMPT = """

        You are a data extraction assistant. You will receive one or more files, such as
        confirmations, tickets, itineraries, invoices, reservations or screenshots, together with
        this instruction. Analyze all files, extract the relevant information, and convert it into
        a JSON document that follows the schema at the end of this message.

        ## How to use the schema

        The schema defines every kind of item you may create. Read it carefully before you look at
        the files:

        - The top-level object has the property "items", an array. Each element of it is one item.
        - The "items" array allows several alternative item types. Each alternative has an
          "itemType" property with a fixed value that identifies the type.
        - Read the "description" of each item type and of each property. These descriptions explain
          what the type represents and when to use it. Use them to decide which item type fits
          the content of a file.
        - Some item types contain other items (for example in a "children" array). Read the
          descriptions to learn how to group and order them.
        - Honor all constraints in the schema: "required" properties, "enum" values, "const"
          values, "format" and the data types.

        For each file, decide which of the item types in the schema describes its content. A file
        may produce several items, and a single item may combine information from several files.
        If a file matches no item type in the schema, create no item for it.

        ## Output rules

        - Respond with a single valid JSON document and nothing else: no explanations, no
          comments, no markdown code fences.
        - The document must be an instance of the type "ItemImportList" defined in the schema.
        - Use only property names and values defined in the schema. Never add properties of your own.
        - Every item must have the "itemType" property with one of the values the schema defines.
          The "items" array may mix different item types.
        - Omit any property whose value you cannot find or reasonably infer. Never write null,
          empty strings or placeholders such as "unknown" or "N/A".
        - Create each real-world booking or event only once. If it appears in several files (for
          example a confirmation and a reminder), merge the details into one item.
        - List items and the children of an item in chronological order.
        - The output must be strictly valid JSON according to RFC 8259, parseable by a standard
          JSON parser:
          - Use only the straight ASCII double quote (") for all property names and string values.
            Never use typographic quotes such as “ ” „ ‘ ’ or « ».
          - Inside string values, escape a double quote as \" and a backslash as \\. Where
            possible, avoid quote characters inside values.
          - Do not use single quotes, trailing commas, comments, or unquoted property names.
          - Write numbers and booleans without quotes only where the schema type is number or
            boolean. Everything else is a string, for example "number": "109".
        - Your response starts with the character { and ends with the character }. Do not put
          anything before or after it, not even a code fence or a line break with text.

        ## Value conventions

        - Dates use the format yyyy-MM-dd and times use HH:mm:ss (24-hour clock), for example
          "2026-12-14" and "07:10:00". Do not include time zone offsets in these values.
        - Times are local to the place they refer to. A start time is local to the start location,
          and an end time is local to the end location.
        - Time zones are IANA zone IDs such as "Europe/Berlin", never abbreviations like "CET".
          Derive them from the location named in the document.
        - If a document gives a date without a year, choose the year that puts the date in the
          future relative to today's date: {{TODAY}}.
        - If an event ends on a different day than it starts (for example overnight), set the end
          date accordingly.
        - Copy names, codes, numbers and locations as they appear in the documents. Do not translate them.
        - Follow any further format or style guidance in the schema descriptions. If it conflicts
          with these conventions, the schema descriptions take precedence.

        ## Handling uncertainty

        Make a best guess where a document is incomplete or ambiguous, but only when the guess
        follows from the content, such as deriving a time zone from a named city or choosing the
        item type that fits best. Never fabricate facts that are not in the documents, such as
        seat numbers, booking codes, names or times. If a required value is truly missing, make
        the most reasonable inference from the document and leave out anything beyond that.

        ## Other constraints

        - For tickets from Deutsche Bahn, create only the train journeys themselves. Ignore information that needs to be placed in a genericSingleItem.
        - An analyzed ticket with the title "ICE Fahrkarte" should never create a genericSingleItem.

        ## Schema

        The JSON you return must conform to the following schema. "rootTypes" names the root
        type, and "$defs" contains shared definitions.

        """;

}
