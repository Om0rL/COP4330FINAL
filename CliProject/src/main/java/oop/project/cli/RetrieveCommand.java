package oop.project.cli;

public class RetrieveCommand {

    //seal the whole parsing thing and make it accessible with only this class
    public static ParsedCommand parse(String input) throws ParseException {
//        var lexed = new Lexer(input).lex();
//        System.out.println(lexed);
//        var parsed = new Parser(lexed).parse();
//        System.out.println(parsed);
//        return null;
        return new Parser(new Lexer(input).lex()).parse();
    }

}
