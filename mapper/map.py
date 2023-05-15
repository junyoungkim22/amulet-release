import sys
import re

def getVarDecl(l):
    matchObj = re.match(r'(var((\s)*))?([0-9a-zA-Z]*)((\s)*)=(.)+;', l, re.M)
    if matchObj:
        if 'Java' in l.split('=')[1]:
            return None
        return matchObj.group(4)
    return None

def is_paren_end(found_brackets, open):
    return (found_brackets) and (open == 0)

def catchup(lines, consumed_chars, lineno):
    ret = 0
    curr_consumed = 0
    curr_lineno = lineno
    while(curr_consumed < consumed_chars):
        curr_consumed += len(lines[curr_lineno])
        curr_lineno += 1
        ret += 1
    return ret



def find_parens(src_code, start, mode):
    open_char = '('
    close_char = ')'
    if mode == 1:
        open_char = '{'
        close_char = '}'

    open = 0
    found_brackets = False
    ret = ''
    curr = start
    while(not is_paren_end(found_brackets, open)):
        char = src_code[curr]
        if char is open_char:
            found_brackets = True
            open += 1
        elif char is close_char:
            open -= 1
        if found_brackets:
            ret += char
        curr += 1
    return ret[1:-1], curr


if __name__ == '__main__':
    js_file = open((sys.argv[1]), 'r')
    lines = js_file.readlines()

    tmp_js_file = open((sys.argv[1]) + '.tmp', 'w')
    array_decls = []
    var_decls = []
    prog_str = ''
    lineno = 0
    while lineno < len(lines):
        l = lines[lineno]
        var_decl = getVarDecl(l)
        if var_decl:
            var_decls.append(var_decl)
            tmp_js_file.write(l)
        elif l == '"adaptive execution";\n' or l == '"amulet";\n':
            lineno += 1
            rest_src_code = ''.join(lines[lineno:])
            curr = 0
            forcond, curr = find_parens(rest_src_code, curr, 1)
            #ifstmt, curr = find_parens(rest_src_code, curr, 1)
            #ifcond, ifcond_end = find_parens(ifstmt, 0, 0)
            #ifbody = ifstmt[ifcond_end:]

            tmp_js_file.write("prog = `\n")
            tmp_js_file.write(rest_src_code[:curr])
            #tmp_js_file.write("for(" + forcond + "){\n")
            #tmp_js_file.write("\tif(" + ifcond + ")" + ifbody + "\n")
            tmp_js_file.write("`\n")

            tmp_js_file.write('JavaMap = Java.type("java.util.HashMap");\n')
            tmp_js_file.write('varMap = new JavaMap();\n')
            for decl in var_decls:
                tmp_js_file.write('varMap.put("' + decl +'", ' + decl + ');\n')

            tmp_js_file.write('LoopNode = Polyglot.import("selectAST");\n')
            tmp_js_file.write('LoopNode(prog, varMap);\n')

            for decl in var_decls:
                tmp_js_file.write(decl + ' = varMap.get("' + decl + '");\n')

            lineno += catchup(lines, curr, lineno)
            lineno -= 1
        else:
            tmp_js_file.write(l)
        lineno += 1

    tmp_js_file.close()