import {
  Box,
  Button,
  Checkbox,
  Input,
  Radio,
  RadioGroup,
  Select,
  SimpleGrid,
  Stack,
  Text,
} from "@chakra-ui/react";
import FormInput from "../components/FormInput";
import { useState } from "react";

const Dashboard = () => {
  const [history, setHistory] = useState("false");

  return (
    <Box backgroundColor="offwhite" h="100vh">
      <Box paddingTop="100px" marginX="310px" justifyContent="center">
        <Text
          fontFamily="poppins.400"
          fontSize="3xl"
          sx={{ textAlign: "center" }}
          marginBottom="70px"
        >
          Preencha as informações abaixo para continuar
        </Text>
        <SimpleGrid columns={2} spacing={10}>
          <FormInput placeholder="Nome" />
          <FormInput placeholder="Data de nascimento" />
          <FormInput placeholder="Peso" />
          <FormInput placeholder="Altura" />
          <Select height="60px" placeholder="Gênero" fontFamily="poppins.500">
            <option value="M">Masculino</option>
            <option value="F">Feminino</option>
          </Select>
          <FormInput placeholder="Tempo médio de exercícios (em minutos)" />
        </SimpleGrid>

        <Text fontFamily="poppins.500" marginTop="40px">
          Doencas na familia?
        </Text>
        <RadioGroup onChange={setHistory} value={history} marginY="30px">
          <Stack>
            <Radio colorScheme="blackAlpha" size="lg" value={"false"}>
              Nao
            </Radio>
            <Radio colorScheme="blackAlpha" size="lg" value={"true"}>
              Sim
            </Radio>
          </Stack>
        </RadioGroup>
        {history === "true" && <FormInput placeholder="Quais?" />}
        <Text fontFamily="poppins.500" marginTop="40px" marginBottom="20px">
          Termos de uso:
        </Text>
        <Text color="gray">
          * Para obter um diagnóstico preciso e recomendações específicas, é
          importante consultar um médico. Além disso, se os sintomas persistirem
          ou piorarem, é essencial buscar atendimento médico imediatamente.
        </Text>
        <Box display="flex" justifyContent="space-between" marginTop="30px">
          <Checkbox
            size="lg"
            colorScheme="blackAlpha"
            borderColor="black"
            borderRadius="8px"
            defaultChecked
          >
            <Text fontSize="small">Li e concordo com os termos de uso</Text>
          </Checkbox>
          <Button color="brand.900" backgroundColor="black" width="150px">
            Salvar
          </Button>
        </Box>
      </Box>
    </Box>
  );
};

export default Dashboard;
