/*
 --- Part Two ---
Your calculation isn't quite right. It looks like some of the digits are actually spelled out with letters: one, two, three, four, five, six, seven, eight, and nine also count as valid "digits".

Equipped with this new information, you now need to find the real first and last digit on each line. For example:

two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen
In this example, the calibration values are 29, 83, 13, 24, 42, 14, and 76. Adding these together produces 281.

What is the sum of all of the calibration values?
*/

namespace day_1;

internal class Part2(string filePath)
{
    private readonly string _filePath = filePath;
    private readonly Dictionary<string, char> _digits = new()
    {
        { "one", '1' },
        { "two", '2' },
        { "three", '3' },
        { "four", '4' },
        { "five", '5' },
        { "six", '6' },
        { "seven", '7' },
        { "eight", '8' },
        { "nine", '9' }
    };

    public long Result { get; private set; }

    public void Run()
    {
        var sum = 0L;

        var obfuscatedCalibrationValues = File.ReadAllLines(_filePath).ToList();
        foreach (var obfuscatedCalibrationValue in obfuscatedCalibrationValues)
        {
            var firstDigit = GetDigit(obfuscatedCalibrationValue, Occurrence.First);
            var lastDigit = GetDigit(obfuscatedCalibrationValue, Occurrence.Last);

            if (firstDigit == null || lastDigit == null)
            {
                continue;
            }

            var parsed = int.TryParse($"{firstDigit}{lastDigit}", out var calibrationValue);
            if (parsed)
            {
                sum += calibrationValue;
            }
        }

        Result = sum;
    }

    private char? GetDigit(string value, Occurrence occurrence)
    {
        var properValue = occurrence == Occurrence.First ? value : value.Reverse();
        for (var i = 1; i <= properValue.Length; i++)
        {
            if (char.IsNumber(properValue[i - 1]))
            {
                return properValue[i - 1];
            }

            foreach (var number in _digits)
            {
                if (properValue[..i].Contains(occurrence == Occurrence.First ? number.Key : number.Key.Reverse()))
                {
                    return number.Value;
                }
            }
        }

        return null;
    }
}

public enum Occurrence
{
    First,
    Last
}

public static class Extensions
{
    public static string Reverse(this string @this)
    {
        var charArray = @this.ToCharArray();
        Array.Reverse(charArray);
        return new string(charArray);
    }
}
