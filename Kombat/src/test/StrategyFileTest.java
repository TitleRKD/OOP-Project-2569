package test;

import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.Tokenizer;
import player.MinionType;
import strategy.Core.Strategy;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StrategyFileTest {

    @Test
    void testComplexStrategy() throws Exception {
        // อ่านไฟล์
        String code = Files.readString(Path.of("src/test/resources/complex.strat"));

        // ลบคอมเมนต์ (ถ้า parser ยังไม่รองรับ)
        code = removeComments(code);

        // แปลงเป็น tokens และ parse
        Strategy strat = new Parser(new Tokenizer(code).tokenize()).parseStrategy();

        // ตรวจสอบว่า parse สำเร็จ
        assertNotNull(strat);

        // สร้าง MinionType
        MinionType type = new MinionType("Complex", 1, strat);

        // TODO: ทดสอบเพิ่มเติมตามต้องการ
        System.out.println("Parse successful!");
    }

    // method ช่วยลบคอมเมนต์ (ถ้า tokenizer ยังไม่รองรับ #)
    private String removeComments(String code) {
        StringBuilder result = new StringBuilder();
        String[] lines = code.split("\n");
        for (String line : lines) {
            int commentIndex = line.indexOf('#');
            if (commentIndex != -1) {
                line = line.substring(0, commentIndex);
            }
            result.append(line).append("\n");
        }
        return result.toString();
    }
}