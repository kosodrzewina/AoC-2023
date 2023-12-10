public class Main {
    public static void main(String[] args) {
        var part1 = new Part1("input.txt");
        var part2 = new Part2("input.txt");

        part1.run();
        part2.run();

        System.out.println(part1.getResult());
        System.out.println(part2.getResult());
    }
}
