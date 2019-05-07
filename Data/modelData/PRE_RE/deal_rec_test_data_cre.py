import re
import jieba
def get_re_data(data):
    re_data = []
    pattern = re.compile(r'((((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._年])?(10|12|0?[13578])([-\/\._月])?(3[01]|[12][0-9]|0?[1-9])日?)|(((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._年])?(11|0?[469])([-\/\._月])?(30|[12][0-9]|0?[1-9])日?)|(((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._年])?(0?2)([-\/\._月])?(2[0-8]|1[0-9]|0?[1-9])日?)|(([2468][048]00)([-\/\._年])?(0?2)([-\/\._月])?(29)日?)|(([3579][26]00)([-\/\._年])?(0?2)([-\/\._月])?(29)日?)|(([1][89][0][48])([-\/\._年])?(0?2)([-\/\._月])?(29)日?)|(([2-9][0-9][0][48])([-\/\._年])?(0?2)([-\/\._月])?(29)日?)|(([1][89][2468][048])([-\/\._年])?(0?2)([-\/\._月])?(29)日?)|(([2-9][0-9][2468][048])([-\/\._年])?(0?2)([-\/\._月])?(29)日?)|(([1][89][13579][26])([-\/\._年])?(0?2)([-\/\._月])?(29)日?)|(([2-9][0-9][13579][26])([-\/\._年])?(0?2)([-\/\._月])?(29)日?))')
    result = pattern.findall(data)
    # print(result)
    for _ in result: 
        ans = ""
        ansLen = 0
        for item in _:
            if ansLen < len(item):
                ansLen = len(item)
                ans = item
        # print(ans)
        re_data.append(ans)
    return re_data

def get_entity_dict():
	time_dict = []
	with open("../dict/time_item.txt","r") as f:
		for line in f.readlines():
			time_dict.append(line.split('\t')[0])
	f.close()

	event_dict = []
	with open("../dict/event_item.txt","r") as f:
		for line in f.readlines():
			event_dict.append(line.split('\t')[0])
	f.close()
	return time_dict,event_dict

def get_new_text():
	new_text = ""
	old_pre_text_list = []
	old_true_text_list = []
	with open("../test.rst","r") as f:
		for line in f.readlines():
			if(len(line.split("\t"))!=4):
				continue
			new_text += line.split("\t")[0]
			old_pre_text_list.append((line.split("\t")[0]+'/'+line.split("\t")[3]).replace("\n",""))
			old_true_text_list.append((line.split("\t")[0]+'/'+line.split("\t")[2]).replace("\n",""))

	f.close()

	return new_text, old_pre_text_list, old_true_text_list

def o2bie(result,entity_list,entity_list_dealed_bie):

    cou = 0
    for item in entity_list:
        pos = -1
        while (True):
            pos = result.find(item, pos + 1)
            if pos == -1:
                break
            if result[pos + len(item) + 1 ] == 'T' or result[pos + len(item) + 1 ] == 'E':
                continue
            if(result[pos + len(item)] != '/' or result[pos-1]!=' '):
                continue;
            result = result[:pos] + entity_list_dealed_bie[cou] + result[pos + len(item) + 2:]
        cou += 1
    return result



def mark_bie(entity_list_dealed,entity):
    entity_list_dealed_bie = []
    for item in entity_list_dealed:
        pos = item.find('i')
        item_tmp = item
        if (pos == -1):
            item_tmp = item + "/" + entity + "s"
        else:
            item_tmp = item[:pos - 1] + entity + "b" + item[pos + 1:] + "/" +  entity + "e"
        entity_list_dealed_bie.append(item_tmp)
    return entity_list_dealed_bie


def word2char(data):
	words_list = data.split(" ")
	new_line = ""
	w = h = ""
	for item in words_list:
		try:
			w,h = item.split("/")
			if(len(w)==0 or len(h)==0):
				continue
			if len(w)==1:
				char_list = item + " "
			else:
				print(w,h)
				if(h.find("b",0)!=-1):
					new_pos = h.find("b")
					new_h = h[:new_pos] + 'i' + h[new_pos+1:]
					char_list = w.replace("","/"+ new_h +" ")
					pos = char_list.find(" ",0)
					char_list = char_list[pos+1:]
					pos = char_list.find("/",0)
					char_list = char_list[:pos+1] + h + char_list[pos+1+len(h):]
					#                     print(char_list)
					#                     print(h)
				elif(h.find("e",0)!=-1):
					new_pos = h.find("e")
					new_h = h[:new_pos] + 'i' + h[new_pos+1:]
					char_list = w.replace("","/"+ h +" ")
					pos = char_list.find(" ",0)
					char_list = char_list[pos+1:]
					pos = char_list.find("/",0)
					char_list = char_list[:pos+1] + new_h + char_list[pos+1+len(new_h):]
					#                     print(char_list)
					#                     print(h)
				elif(h.find("s",0)!=-1):
					new_pos = h.find("s")
					new_b_h = h[:new_pos] + 'b' + h[new_pos+1:]
					new_i_h = h[:new_pos] + 'i' + h[new_pos+1:]
					new_e_h = h[:new_pos] + 'e' + h[new_pos+1:]
					char_list = w.replace("","/"+ new_i_h +" ")
					pos = char_list.find(" ",0)
					char_list = char_list[pos+1:]
					pos = char_list.find("/",0)
					char_list = char_list[:pos+1] + new_b_h + char_list[pos+1+len(new_b_h):-1-len(new_e_h)] + new_e_h + char_list[-1:]
					# print(char_list)
					# print(h)
				else:
					char_list = w.replace("","/"+h+" ")
					pos = char_list.find(" ",0)
					char_list = char_list[pos+1:]
					# print(char_list)
					# print(h)
				print(char_list)
				# print(h)

			new_line += char_list
		except:
			pass
	return new_line

if __name__ == '__main__':


	# print("EVENT_E".find("e"))

	new_text, old_pre_text_list, old_true_text_list = get_new_text()
	re_time = get_re_data(new_text)
	time_dict, event_dict = get_entity_dict()

	time_list = []
	event_list = []

	for item in time_dict:
		if(item in new_text):
			time_list.append(item)

	for item in event_dict:
		if(item in new_text):
			event_list.append(item)

	time_list = list(set(time_list + re_time))
	event_list = event_list

	time_list_dealed = []
	for item in time_list:
		document_cut = jieba.cut(item)
		result = "/Ti ".join(document_cut)
		time_list_dealed.append(result)

	event_list_dealed = []
	for item in event_list:
		document_cut = jieba.cut(item)
		result = "/Ei ".join(document_cut)
		event_list_dealed.append(result)



	for item in time_list:
		jieba.add_word(item)

	for item in event_list:
		jieba.add_word(item)

	document_cut = jieba.cut(new_text) # 分词
	result = "/O ".join(document_cut) + "/O " # 将数据全部标注为无关数据，然后再根据time_list中的时间实体进行修改标注
	# print(result)
	for item in time_list:
		jieba.del_word(item)

	for item in event_list:
		jieba.del_word(item)


	time_list_dealed_bie = mark_bie(time_list_dealed,"T")
	event_list_dealed_bie = mark_bie(event_list_dealed,"E")

	#正则预测实体
	ans = o2bie(result, time_list + event_list, time_list_dealed_bie + event_list_dealed_bie)
	ans = word2char(ans)
	ans = ans.replace("Tb","TIME_B").replace("Ti","TIME_I").replace("Te","TIME_E").replace("Ts","TIME_S").replace("Eb","EVENT_B").replace("Ei","EVENT_I").replace("Ee","EVENT_E").replace("Es","EVENT_S")

	#crf预测实体
	old_pre_text = (" ".join(old_pre_text_list)).replace("TIME_B","Tb").replace("TIME_I","Ti").replace("TIME_E","Te").replace("TIME_S","Ts").replace("EVENT_B","Eb").replace("EVENT_I","Ei").replace("EVENT_E","Ee").replace("EVENT_S","Es")
	old_pre_text = word2char(old_pre_text)
	old_pre_text = old_pre_text.replace("Tb","TIME_B").replace("Ti","TIME_I").replace("Te","TIME_E").replace("Ts","TIME_S").replace("Eb","EVENT_B").replace("Ei","EVENT_I").replace("Ee","EVENT_E").replace("Es","EVENT_S")

	#真实实体
	old_true_text = (" ".join(old_true_text_list)).replace("TIME_B","Tb").replace("TIME_I","Ti").replace("TIME_E","Te").replace("TIME_S","Ts").replace("EVENT_B","Eb").replace("EVENT_I","Ei").replace("EVENT_E","Ee").replace("EVENT_S","Es")
	old_true_text = word2char(old_true_text)
	old_true_text = old_true_text.replace("Tb","TIME_B").replace("Ti","TIME_I").replace("Te","TIME_E").replace("Ts","TIME_S").replace("Eb","EVENT_B").replace("Ei","EVENT_I").replace("Ee","EVENT_E").replace("Es","EVENT_S")

	#正则预测实体
	new_pre_text_data = ""
	new_pre_text_list = []
	for item in ans.split(" "):
		if(len(item.split('/'))!=2):
			continue
		new_pre_text_list.append(item.split('/')[0] + '\t' + 'n' + '\t' + item.split('/')[1])
		new_pre_text_data += item.split('/')[0] + '\t' + 'n' + '\t' + item.split('/')[1] + '\n'

	with open("./new_pre_text_data.rst","w") as f:
		f.write(new_pre_text_data)
	f.close()

	#crf预测实体
	old_pre_text_data = ""
	old_pre_text_list = []
	for item in old_pre_text.split(" "):
		if(len(item.split('/'))!=2):
			continue
		old_pre_text_list.append(item.split('/')[0] + '\t' + 'n' + '\t' + item.split('/')[1])
		old_pre_text_data += item.split('/')[0] + '\t' + 'n' + '\t' + item.split('/')[1] + '\n'

	with open("./old_pre_text_data.rst","w") as f:
		f.write(old_pre_text_data)
	f.close()

	#真实实体
	old_true_text_data = ""
	old_true_text_list = []
	for item in old_true_text.split(" "):
		if(len(item.split('/'))!=2):
			continue
		old_true_text_list.append(item.split('/')[0] + '\t' + 'n' + '\t' + item.split('/')[1])
		old_true_text_data += item.split('/')[0] + '\t' + 'n' + '\t' + item.split('/')[1] + '\n'

	with open("./old_true_text_data.rst","w") as f:
		f.write(old_true_text_data)
	f.close()


	# if(False):
	if(len(new_pre_text_list)!=len(old_pre_text_list) or len(new_pre_text_list)!=len(old_true_text_list)):
		print("WRONG")
	else:
		final_test_text_list = []
		final_test_text_data = ""
		for i in range(len(new_pre_text_list)):
			if(new_pre_text_list[i].split("\t")[0] != old_pre_text_list[i].split("\t")[0] or new_pre_text_list[i].split("\t")[0] != old_true_text_list[i].split("\t")[0]):
				print(i)
				print("WRONG")
				break;

			pre_tag = ""
			# pre_tag = old_pre_text_list[i].split("\t")[2]
			# pre_tag = new_pre_text_list[i].split("\t")[2]

			if(new_pre_text_list[i].split("\t")[2] == "O"):
				pre_tag = old_pre_text_list[i].split("\t")[2]
			else:
				pre_tag = new_pre_text_list[i].split("\t")[2]

			cur_text = new_pre_text_list[i].split("\t")[0] + "\t" + "n" + "\t" + old_true_text_list[i].split("\t")[2] + "\t" + pre_tag + "\n"
			final_test_text_data += cur_text
			final_test_text_list.append(cur_text)
			print(cur_text)
		with open("./final_test_text_data.rst","w") as f:
			f.write(final_test_text_data)
		f.close()


















