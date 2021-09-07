package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // final이 붙은 생성자를 자동으로 만들어줌
public class BasicItemController {
    private final ItemRepository itemRepository;


    //@Autowired 스프링빈에서 생성자가 하나면 @Autowired생략가능
//    public BasicItemController(ItemRepository itemRepository) {
//        //this.itemRepository = itemRepository;
//    }//이렇게하면 BasicItemController가 주입되면서 스프링빈으로 주입이됨 스프링빈에서 생성자가 하나면 @Autowired생략가능

    @GetMapping
    public String items(Model model){

        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId,Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute(item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }
    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model)
    {
        Item item=new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item",item);

        return "basic/item";
    }
//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute("item") Item item)
//    {
////        Item item=new Item();
////        item.setItemName(itemName);
////        item.setPrice(price);
////        item.setQuantity(quantity);
//        //@ModelAttribute가 만들어줌
//
//        itemRepository.save(item);
//
//        //model.addAttribute("item",item); 이거를 주석 처리해도됨
//        //@ModelAttribute가 모델(뷰)에 직접 넣어주는 역할(자동추가)도 함
//        return "basic/item";
//    }

    @PostMapping("/add")
    public String addItemV4(Item item)
    {
        //Item->item이라는 이름으로 모델에 담김
        itemRepository.save(item);
        return "basic/item";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,20));
    }

}
