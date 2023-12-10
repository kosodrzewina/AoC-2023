/* 
--- Part Two ---
The engineer finds the missing part and installs it in the engine! As the engine springs to life, you jump in the closest gondola, finally ready to ascend to the water source.

You don't seem to be going very fast, though. Maybe something is still wrong? Fortunately, the gondola has a phone labeled "help", so you pick it up and the engineer answers.

Before you can explain the situation, she suggests that you look out the window. There stands the engineer, holding a phone in one hand and waving with the other. You're going so slowly that you haven't even left the station. You exit the gondola.

The missing part wasn't the only issue - one of the gears in the engine is wrong. A gear is any * symbol that is adjacent to exactly two part numbers. Its gear ratio is the result of multiplying those two numbers together.

This time, you need to find the gear ratio of every gear and add them all up so that the engineer can figure out which gear needs to be replaced.

Consider the same engine schematic again:

467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598..
In this schematic, there are two gears. The first is in the top left; it has part numbers 467 and 35, so its gear ratio is 16345. The second gear is in the lower right; its gear ratio is 451490. (The * adjacent to 617 is not a gear because it is only adjacent to one part number.) Adding up all of the gear ratios produces 467835.

What is the sum of all of the gear ratios in your engine schematic?
*/

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Part2 {
    private final String filePath;
    private Integer result = null;

    public Part2(String filePath) {
        this.filePath = filePath;
    }

    public Integer getResult() {
        return result;
    }

    public void run() {
        var engineSchematic = getEngineSchematic();
        var partNumbers = new ArrayList<PartNumber>();
        var digitBuffer = new StringBuilder();
        var isPartValidated = false;
        var currentGears = new ArrayList<int[]>();

        for (var i = 0; i < engineSchematic.size(); i++) {
            for (var j = 0; j < engineSchematic.get(i).size(); j++) {
                var character = engineSchematic.get(i).get(j);

                if (Character.isDigit(character)) {
                    digitBuffer.append(character);

                    var gears = getAdjacentGears(i, j, engineSchematic);
                    currentGears.addAll(gears);

                    if (Character.isDigit(character) && gears.size() != 0) {
                        isPartValidated = true;
                    }
                } else if (isPartValidated) {
                    Integer number = null;
                    try {
                        number = Integer.parseInt(digitBuffer.toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    var partNumber = new PartNumber(number);
                    for (var gear : currentGears) {
                        if (!partNumber.getGears().stream().anyMatch(g -> g.getX() == gear[1] && g.getY() == gear[0])) {
                            partNumber.addGear(new Gear(gear[1], gear[0]));
                        }
                    }

                    partNumbers.add(partNumber);

                    digitBuffer.setLength(0);
                    isPartValidated = false;
                    currentGears.clear();
                    continue;
                } else {
                    digitBuffer.setLength(0);
                }
            }
        }

        var gears = new ArrayList<Gear>();
        for (var partNumber : partNumbers) {
            var partNumberGears = partNumber.getGears();
            for (var i = 0; i < partNumberGears.size(); i++) {
                final var itsTerrible = i;

                var gear = gears.stream().filter(g -> g.getX() == partNumberGears.get(itsTerrible).getX() && g.getY() == partNumberGears.get(itsTerrible).getY()).findFirst();
                if (!gear.isPresent()) {
                    gears.add(new Gear(partNumberGears.get(i).getX(), partNumberGears.get(i).getY(), partNumber.getNumber()));
                } else {
                    gear.get().addPartNumber(partNumber.getNumber());
                }
            }
        }

        result = gears.stream().mapToInt(Gear::getRatio).sum();
    }

    private List<int[]> getAdjacentGears(int i, int j, List<List<Character>> engineSchematics) {
        var gears = new ArrayList<int[]>();
        var adjacentIndices = Arrays.asList(
            new int[] { i - 1, j },
            new int[] { i - 1, j - 1},
            new int[] { i, j - 1},
            new int[] { i + 1, j - 1 },
            new int[] { i + 1, j },
            new int[] { i + 1, j + 1 },
            new int[] { i, j + 1 },
            new int[] { i - 1, j + 1 }
        );

        for (var adjacentPairOfIndices : adjacentIndices) {
            if (adjacentPairOfIndices[0] > 0 && adjacentPairOfIndices[0] < engineSchematics.size() && adjacentPairOfIndices[1] > 0 && adjacentPairOfIndices[1] < engineSchematics.get(i).size()) {
                var currentCharacter = engineSchematics.get(adjacentPairOfIndices[0]).get(adjacentPairOfIndices[1]);

                if (currentCharacter == '*') {
                    gears.add(adjacentPairOfIndices);
                }
            }
        }

        return gears;
    }

    private List<List<Character>> getEngineSchematic() {
        var engineSchematic = new ArrayList<List<Character>>();

        try {
            var fileInputStream = new FileInputStream(filePath);
            var dataInputStream = new DataInputStream(fileInputStream);
            var bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
    
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                engineSchematic.add(line.chars().mapToObj(ch -> (char)ch).toList());
            }

            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return engineSchematic;
    }
}
