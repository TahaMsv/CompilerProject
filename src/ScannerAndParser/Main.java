package ScannerAndParser;

import CodeGen.CodeGenerator;
import CodeGen.CodeGeneratorImpl;
import CodeGen.SymbolTable.Stacks;
import CodeGen.SymbolTable.SymbolTable;
import CodeGen.Utils.SPIMFileWriter;

import java.io.*;

public class Main {
    public static void parseAndWrite(String outputFilePath, Parser parser) throws IOException {
//        File file = new File(outputFilePath);
//        FileWriter fileWriter = new FileWriter(file);
        try {
            parser.parse();
//            fileWriter.write("Syntax is correct!");
        } catch (Exception e) {
//            fileWriter.write("Syntax is wrong!");
        }
//        fileWriter.close();
    }

    public static void main(String[] args) throws IOException {
        String inputCoolFilePath = "C:\\Users\\Tahamousavi\\Downloads\\Telegram Desktop\\test.cool";
        String outputFilePath = "";
        String tablePath = "D:\\Sbu\\Semester 5\\Compiler\\Project\\Compiler1400\\src\\ScannerAndParser\\table.npt";
//        if (args.length >= 6) {
//            for (int i = 0; i < args.length; i++) {
//                if (args[i].equals("--input")) {
//                    inputCoolFilePath = args[i + 1];
//                }
//                if (args[i].equals("--output")) {
//                    outputFilePath = args[i + 1];
//                }
//                if (args[i].equals("--table")) {
//                    tablePath = args[i + 1];
//                }
//            }
//        } else {
//            System.out.println("Run like bellow:\njava <javaClassFile> --input <inputCoolFilePath> --output <outputFilePath> --table <tablePath>");
//            return;
//        }
        // inputCoolFilePath can be like this: ./test/test1.cool
        // outputFilePath can be like this: ./out/test1.txt
        // tablePath can be like this: ./src/table.npt

        // Make a new instance of your parser that reads scanner tokens
        // and then call "parse" method of your parser

        // write the result of parsing in the outputFilePath.
        // if the syntax is correct you should write "Syntax is correct!"
        // and if the syntax is wrong, you should write "Syntax is wrong!" in outputFilePath.
        SPIMFileWriter writer = new SPIMFileWriter("src/CodeGen/Utils/");
        Stacks.pushSymbolTableS(new SymbolTable("main"));
        LexicalScanner scanner = new LexicalScanner(new FileReader(inputCoolFilePath));
        CodeGenerator codeGenerator = new CodeGeneratorImpl();
        CodeGeneratorImpl.initCodeGenerator(scanner);
        Parser parser = new Parser(scanner, codeGenerator, tablePath, true);

        parseAndWrite(outputFilePath, parser);
    }
}