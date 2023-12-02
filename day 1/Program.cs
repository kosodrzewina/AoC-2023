using day_1;

var inputFile = args[0];

var part1 = new Part1(inputFile);
var part2 = new Part2(inputFile);

part1.Run();
part2.Run();

Console.WriteLine(part1.Result);
Console.WriteLine(part2.Result);
