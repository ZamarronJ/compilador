import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private static final Map<String, TipoToken> palabrasReservadas;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and",    TipoToken.AND);
        palabrasReservadas.put("else",   TipoToken.ELSE);
        palabrasReservadas.put("false",  TipoToken.FALSE);
        palabrasReservadas.put("for",    TipoToken.FOR);
        palabrasReservadas.put("fun",    TipoToken.FUN);
        palabrasReservadas.put("if",     TipoToken.IF);
        palabrasReservadas.put("null",   TipoToken.NULL);
        palabrasReservadas.put("or",     TipoToken.OR);
        palabrasReservadas.put("print",  TipoToken.PRINT);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("true",   TipoToken.TRUE);
        palabrasReservadas.put("var",    TipoToken.VAR);
        palabrasReservadas.put("while",  TipoToken.WHILE);
    }

    private final String source;

    private final List<Token> tokens = new ArrayList<>();
    
    public Scanner(String source){
        this.source = source + " ";
    }

    /**
     * @return
     * @throws Exception
     */
    public List<Token> scan() throws Exception {
        int estado = 0;
        String lexema = "";
        String literal = "";
        char c;
        int linea=0;

        for(int i=0; i<source.length(); i++){
            c = source.charAt(i);
            if (c == '\n'){
                linea++;
            }
            switch (estado){
                case 0:
                    if(Character.isLetter(c)){
                        estado = 13;
                        lexema += c;
                    }
                    else if(Character.isDigit(c)){
                        estado = 15;
                        lexema += c;

                        /*while(Character.isDigit(c)){
                            lexema += c;
                            i++;
                            c = source.charAt(i);
                        }
                        Token t = new Token(TipoToken.NUMBER, lexema, Integer.valueOf(lexema));
                        lexema = "";
                        estado = 0;
                        tokens.add(t);
                        */      
                    }
                    else if(c=='"'){
                        estado = 24;
                        lexema += c;
                    }
                    else if(c=='.'){
                        
                        lexema = ".";
                        Token t = new Token(TipoToken.DOT, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }
                    else if(c=='+'){
                        lexema = "+";
                        Token t = new Token(TipoToken.PLUS, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }
                    else if(c=='-'){
                        lexema = "-";
                        Token t = new Token(TipoToken.MINUS, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }
                    else if(c=='('){
                        lexema = "(";
                        Token t = new Token(TipoToken.LEFT_PAREN, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }
                    else if(c==')'){
                        lexema = ")";
                        Token t = new Token(TipoToken.RIGHT_PAREN, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }
                    else if(c=='{'){
                        lexema = "{";
                        Token t = new Token(TipoToken.LEFT_BRACE, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }
                    else if(c=='}'){
                        lexema = "}";
                        Token t = new Token(TipoToken.RIGHT_BRACE, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }
                    else if(c==','){
                        lexema = ",";
                        Token t = new Token(TipoToken.COMMA, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }
                    else if(c==';'){
                        lexema = ";";
                        Token t = new Token(TipoToken.SEMICOLON, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }

                    break;

                case 13:
                    if(Character.isLetterOrDigit(c)){
                        estado = 13;
                        lexema += c;
                    }
                    else{
                        TipoToken tt = palabrasReservadas.get(lexema);

                        if(tt == null){
                            Token t = new Token(TipoToken.IDENTIFIER, lexema);
                            tokens.add(t);
                        }
                        else{
                            Token t = new Token(tt, lexema);
                            tokens.add(t);
                        }

                        estado = 0;
                        lexema = "";
                        i--;

                    }
                    break;

                case 15:
                    if(Character.isDigit(c)){
                        estado = 15;
                        lexema += c;
                    }
                    else if(c == '.'){
                        estado=16;
                        lexema += c;
                    }
                    else if(c == 'E'){
                        estado=18;
                        lexema += c;
                    }
                    else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Integer.valueOf(lexema));
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 16:
                    if(Character.isDigit(c)){
                        estado = 17;
                        lexema += c;
                    }
                    break;
                case 17:
                    if(Character.isDigit(c)){
                        estado = 17;
                        lexema += c;
                    }
                    else if(c == 'E'){
                        estado=18;
                        lexema += c;
                    }
                    else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Double.valueOf(lexema));
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 18:
                    if(c=='+'||c=='-'){
                        estado = 19;
                        lexema += c;
                    }
                    else if(Character.isDigit(c)){
                        estado = 20;
                        lexema += c;
                    }
                    break;
                case 19:
                    if(Character.isDigit(c)){
                        estado = 20;
                        lexema += c;
                    }
                    break;
                case 20:
                    if(Character.isDigit(c)){
                        estado = 20;
                        lexema += c;
                    }
                    else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Double.valueOf(lexema));
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 24:
                    if(c == '\n'){
                        System.out.println("Error en la linea "+linea);
                        System.exit(0);
                    }
                    else if(c == '"'){
                        lexema +=c;
                        literal=lexema.replace('"',' ').trim();    
                        Token t = new Token(TipoToken.STRING, lexema, literal);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }
                    else{
                        lexema+=c;
                        estado=24;
                    }
                    break;                
            }
        }
        System.out.println("lineas totales" + linea);
        return tokens;
    }
}
