package main

import (
	"fmt"
	"os"

	"github.com/kosodrzewina/AoC-2023/day-4/part1"
)

func main() {
	if len(os.Args) != 2 {
		fmt.Println("The program takes only one argument (input file path)")
	}

	filePath := os.Args[1]

	part1.Run(filePath)
}
