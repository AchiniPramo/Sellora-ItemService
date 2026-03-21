package lk.ijse.eca.itemservice.mapper;

import lk.ijse.eca.itemservice.dto.ItemDto;
import lk.ijse.eca.itemservice.entity.Item;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ItemMapper {

    ItemDto toDto(Item item);

    Item toEntity(ItemDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "itemId", ignore = true)
    void updateEntity(ItemDto dto, @MappingTarget Item item);
}
