package main

import (
	"fmt"
	"os"

	"github.com/kosodrzewina/AoC-2023/day-4/part1"
	"github.com/kosodrzewina/AoC-2023/day-4/part2"
)

func main() {
	if len(os.Args) != 2 {
		fmt.Println("The program takes only one argument (input file path)")
		os.Exit(1)
	}

	filePath := os.Args[1]

	part1.Run(filePath)
	part2.Run(filePath)
}
