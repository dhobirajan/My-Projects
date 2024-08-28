class T20:
    def __init__(self):
        self.players_info = []
        self.players_wickets = []
        self.data_entered = False

    def player_info(self, num_players):
        for i in range(1,num_players+1):
            name = input(f"Enter player {i} name :")
            runs = int(input("Enter runs scored: "))
            self.players_info.append((name, runs))
        self.data_entered = True

    def wicket(self, num_players):
        for i in range(1,num_players+1):
            name = input(f"Enter player {i} name : ")
            wickets = int(input("Enter wickets taken: "))
            self.players_wickets.append((name, wickets))
        self.data_entered = True

    def heapify(self, arr, n, i, key):
        largest = i
        l = 2 * i + 1
        r = 2 * i + 2

        if l < n and arr[l][key] > arr[largest][key]:
            largest = l

        if r < n and arr[r][key] > arr[largest][key]:
            largest = r

        if largest != i:
            arr[i], arr[largest] = arr[largest], arr[i]
            self.heapify(arr, n, largest, key)

    def heap_sort(self, arr, key):
        n = len(arr)

        for i in range(n // 2 - 1, -1, -1):
            self.heapify(arr, n, i, key)

        for i in range(n - 1, 0, -1):
            arr[i], arr[0] = arr[0], arr[i]
            self.heapify(arr, i, 0, key)

    def sorted(self, data, key):
        self.heap_sort(data, key)

    def display_run(self):
        if not self.data_entered:
            print("No data entered yet. Please enter data first.")
            return
        if not self.players_info:
            print("No data available.")
            return
        self.sorted(self.players_info, 1)
        self.players_info.reverse()  # Reverse the sorted list
        print("Rank\tName\tRuns")
        for i, player in enumerate(self.players_info, 1):
            print(f"{i}\t{player[0]}\t{player[1]}")

    def display_wicket(self):
        if not self.data_entered:
            print("No data entered yet. Please enter data first.")
            return
        if not self.players_wickets:
            print("No data available.")
            return
        self.sorted(self.players_wickets, 1)
        self.players_wickets.reverse()  # Reverse the sorted list
        print("Rank\tName\tWickets")
        for i, player in enumerate(self.players_wickets, 1):
            print(f"{i}\t{player[0]}\t{player[1]}")

    def delete_run_data(self):
        if not self.data_entered:
            print("No data entered yet. Please enter data first.")
            return
        name = input("Enter player name to delete run data: ")
        for player in self.players_info:
            if player[0] == name:
                self.players_info.remove(player)
                print("Run data deleted successfully.")
                return
        print("Player not found.")

    def delete_wicket_data(self):
        if not self.data_entered:
            print("No data entered yet. Please enter data first.")
            return
        name = input("Enter player name to delete wicket data: ")
        for player in self.players_wickets:
            if player[0] == name:
                self.players_wickets.remove(player)
                print("Wicket data deleted successfully.")
                return
        print("Player not found.")

    def update_data_run(self):
        if not self.data_entered:
            print("No data entered yet. Please enter data first.")
            return
        name = input("Enter player name to update: ")
        for i, player in enumerate(self.players_info):
            if player[0] == name:
                print("What data do you want to update?")
                print("1. Name")
                print("2. Runs")
                print("3. Both Name and Runs")
                choice = int(input("Enter your choice: "))
                if choice == 1:
                    new_name = input("Enter new name: ")
                    self.players_info[i] = (new_name, player[1])
                elif choice == 2:
                    new_runs = int(input("Enter new runs: "))
                    self.players_info[i] = (player[0], new_runs)
                elif choice == 3:
                    new_name = input("Enter new name: ")
                    new_runs = int(input("Enter new runs: "))
                    self.players_info[i] = (new_name, new_runs)
                else:
                    print("Invalid choice.")
                    return
                self.sorted(self.players_info, 1)
                print("Data updated successfully.")
                return
        print("Player not found.")

    def update_wicket(self):
        if not self.data_entered:
            print("No data entered yet. Please enter data first.")
            return
        name = input("Enter player name to update: ")
        for i, player in enumerate(self.players_wickets):
            if player[0] == name:
                print("What data do you want to update?")
                print("1. Name")
                print("2. Wickets")
                print("3. Both Name and Wickets")
                choice = int(input("Enter your choice: "))
                if choice == 1:
                    new_name = input("Enter new name: ")
                    self.players_wickets[i] = (new_name, player[1])
                elif choice == 2:
                    new_wickets = int(input("Enter new wickets: "))
                    self.players_wickets[i] = (player[0], new_wickets)
                elif choice == 3:
                    new_name = input("Enter new name: ")
                    new_wickets = int(input("Enter new wickets: "))
                    self.players_wickets[i] = (new_name, new_wickets)
                else:
                    print("Invalid choice.")
                    return
                self.sorted(self.players_wickets, 1)
                print("Data updated successfully.")
                return
        print("Player not found.")

    def exit_program(self):
        print("Thanks for visiting!")
        exit()

    def search_player(self, name):
        if not self.data_entered:
            print("No data entered yet. Please enter data first.")
            return
        for i, player in enumerate(self.players_info):
            if player[0] == name:
                print(f"Rank: {i + 1}\tName: {player[0]}\tRuns: {player[1]}")
                return
        for i, player in enumerate(self.players_wickets):
            if player[0] == name:
                print(f"Rank: {i + 1}\tName: {player[0]}\tWickets: {player[1]}")
                return
        print("Player not found.")

def main():
    t20 = T20()
    print(".................................")
    print(".........WELCOME................. ")
    print(".................................")
    while True:
        print("\n1. Add Player Information of Runs")
        print("2. Add Player Information of Wickets")
        print("3. Display Sorted Runs")
        print("4. Display Sorted Wickets")
        print("5. Delete Run Data")
        print("6. Delete Wicket Data")
        print("7. Update Player Runs")
        print("8. Update Player Wickets")
        print("9. Search Player")
        print("10. Exit")
        choice = int(input("Enter your choice: "))
        if choice == 1:
            num_players = int(input("Enter the number of players: "))
            t20.player_info(num_players)
        elif choice == 2:
            num_players = int(input("Enter the number of players: "))
            t20.wicket(num_players)
        elif choice == 3:
            t20.display_run()
        elif choice == 4:
            t20.display_wicket()
        elif choice == 5:
            t20.delete_run_data()
        elif choice == 6:
            t20.delete_wicket_data()
        elif choice == 7:
            t20.update_data_run()
        elif choice == 8:
            t20.update_wicket()
        elif choice == 9:
            name = input("Enter player name to search: ")
            t20.search_player(name)
        elif choice == 10:
            t20.exit_program()
        else:
            print("Invalid choice. Please try again.")

if __name__ == "__main__":
    main()
