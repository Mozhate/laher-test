// 语法：第一个[]指示表达式的范围，之后部分是规则中使用的表达式，等号（“ =”）右边的部分是表达式到规则语言的映射
// 语法：[<scope>][<type>]<language>=<dslr rule mapping>
// scope范围类型：when=condition，then=consequence，*，keyword

[when]如果有人叫"{name}"=Person(name=="{name}")
[then]"{res}"=System.out.println("{res}");