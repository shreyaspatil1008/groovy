package examples

class PirateExtension {
    static String likeAPirate(final String self) {
        // List of pirate language translations.
        def translations = [
            ["hello", "ahoy"], ["Hi", "Yo-ho-ho"],
            ['are', 'be'], ['am', 'be'], ['is', 'be'],
            ['the', "th'"], ['you', 'ye'], ['your', 'yer'],
            ['of', "o'"]
        ]
        
        // Translate the original String to a 
        // pirate language String.
        String result = self
        translations.each { translate ->
                result = result.replaceAll(translate[0], translate[1])
        }
        result
    }
}
