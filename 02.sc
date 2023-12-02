using import Array radl.IO.FileStream radl.String+ String slice
from ((import C.bindings) . extern) let sscanf strcmp

try
    f := FileStream "02.txt" FileMode.Read
    max-red max-green max-blue := 12, 13, 14
    local id-sum : i32
    local total-power : i32

    for i l in (enumerate ('lines f))
        match? start end := scan l ":"
        if match? # this wil fail if the line is empty, like at the end of the input
            data := rslice l end
            draws := split data ";"

            local success? : bool = true
            local needed-red : i32
            local needed-green : i32
            local needed-blue : i32

            for draw in draws
                local red : i32
                local green : i32
                local blue : i32
                for cube in (split draw ",")
                    cube-type := alloca-array i8 6
                    local amount : i32
                    count := sscanf cube " %d %5s" &amount cube-type

                    cmp := (a b) -> ((strcmp a b) == 0)

                    if (count == 2)
                        if (cmp cube-type "green")
                            green = amount
                        elseif (cmp cube-type "blue")
                            blue = amount
                        elseif (cmp cube-type "red")
                            red = amount
                if (red > max-red or green > max-green or blue > max-blue)
                    success? = false

                needed-red = max needed-red red
                needed-green = max needed-green green
                needed-blue = max needed-blue blue

            id-sum += (i + 1) # 1-based index
            total-power += needed-red * needed-green * needed-blue

    print "A:" id-sum
    print "B:" total-power
else ()
