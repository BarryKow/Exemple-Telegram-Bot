import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi=new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        }catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

    }

    public void onUpdateReceived(Update update) {  //метод приёма сообщений.Получаем обновления через
                                                    // лонгпул (так же можно через вэбхуки
    Message message=update.getMessage(); // получаем сообщение из объекта update
    if (message !=null && message.hasText()){
        switch (message.getText()){
            case "/help":
                sendMsg(message,"чем могу помочь?")
                        break;
            case "/setting":
                sendMsg(message,"что будем настраивать?")
                        break;
            default :  // ответ по умолчанию  на /start
        }
    }
    }

    public void setButtons (SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup=new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup); //связываю сообщение и клавиатуру
        replyKeyboardMarkup.setSelective(true); // выводит клавиатуру всем пользователям
        replyKeyboardMarkup.setResizeKeyboard(true); //подгонка клавиатуры под размер
        replyKeyboardMarkup.setOneTimeKeyboard(false);// не скрываем клавиатуру после нажатия

        //создание кнопок
        List <KeyboardRow> keyboardRowList=new ArrayList<KeyboardRow>();
        KeyboardRow keyboardFirstRow=new KeyboardRow(); // инициализация первой строки

        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("/setting"));

        keyboardRowList.add(keyboardFirstRow);//добавление строк клавиатуры в список
        replyKeyboardMarkup.setKeyboard(keyboardRowList);  //устанавливаем список

        //что бы всё заработало, помещаем клавиатуру в отправку сообщений

    }

    public void sendMsg (Message message, String text){
        SendMessage sndMsg=new SendMessage();
        sndMsg.enableMarkdown(true);
        sndMsg.setChatId(message.getChatId());
        sndMsg.setReplyToMessageId(message.getMessageId());
        sndMsg.setText(text);
        try {
            setButtons(sndMsg);
            sendMessage(sndMsg);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() { // возврат имени бота
        return "MyTestVebinarBot";
    }

    public String getBotToken() { // возврат токена регистрации
        return "some token";
    }
}
