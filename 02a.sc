using import Array radl.IO.FileStream radl.String+ String slice
from ((import C.bindings) . extern) let sscanf strcmp

try
    f := FileStream "02.txt" FileMode.Read
    max-red max-green max-blue := 12, 13, 14
    local id-sum : i32

    for i l in (enumerate ('lines f))
        match? start end := scan l ":"
        if match? # this wil fail if the line is empty, like at the end of the input
            data := rslice l end
            draws := split data ";"

            :: fail
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
                    merge fail
            id-sum += (i + 1) # 1-based index
            fail ::

    print id-sum
else ()
