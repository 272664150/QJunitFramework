#coding=utf-8

import os, sys

# 设置需要的文件类型,可设置多个类型
filter = [".xml"] 

def all_file_path(dir_name):
    # 所有的xml文件
    result = []
    for main_dir, sub_dir, file_name_list in os.walk(dir_name):
        for file_name in file_name_list:
            # 合并成一个完整路径
            full_path = os.path.join(main_dir, file_name)
            # 获取文件后缀
            ext = os.path.splitext(full_path)[1]
            if ext in filter:
                result.append(full_path)
    return result


def xml_to_html(file_path):
    os.system("python -m junit2htmlreport %s" %(file_path))


args_length = len(sys.argv)
if args_length < 2:
    print('\n where is the XML test report directory path?')
else:
    path = sys.argv[1].replace('\\', '\\\\')
    if os.path.isdir(path):
        file_path_list = all_file_path(path)
        for file_path in file_path_list:
            xml_to_html(file_path)
    else:
        xml_to_html(path)

    print('Finished!')